[CmdletBinding()]
param()

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$Root = (Resolve-Path (Join-Path $PSScriptRoot "../..")).Path

$SourceFile = Join-Path $Root "data/registry/sb-authorized-entities-2018-2026.csv"
$OutputDirectory = Join-Path $Root "data/registry/processed"
$HistoryFile = Join-Path $OutputDirectory "sb-institutional-history.csv"
$CatalogFile = Join-Path $OutputDirectory "institutions.json"

$MonthNumbers = @{
    ENERO = 1
    FEBRERO = 2
    MARZO = 3
    ABRIL = 4
    MAYO = 5
    JUNIO = 6
    JULIO = 7
    AGOSTO = 8
    SEPTIEMBRE = 9
    OCTUBRE = 10
    NOVIEMBRE = 11
    DICIEMBRE = 12
}

function Normalize-Text {
    param([string]$Value)

    return ($Value.Trim() -replace '\s+', ' ').
        Normalize([Text.NormalizationForm]::FormKC).
        ToUpperInvariant()
}

function Get-StableId {
    param([string]$IdentityKey)

    $sha = [System.Security.Cryptography.SHA256]::Create()

    try {
        $bytes = [System.Text.Encoding]::UTF8.GetBytes($IdentityKey)
        $hash = [BitConverter]::ToString(
            $sha.ComputeHash($bytes)
        ).Replace("-", "").ToLowerInvariant()

        return "sb-$($hash.Substring(0, 20))"
    }
    finally {
        $sha.Dispose()
    }
}

if (-not (Test-Path $SourceFile)) {
    throw "No se encontró el archivo fuente: $SourceFile"
}

New-Item -ItemType Directory -Path $OutputDirectory -Force |
    Out-Null

Write-Host "Leyendo archivo fuente..."

$rows = @(Import-Csv -Path $SourceFile)

if ($rows.Count -eq 0) {
    throw "El archivo fuente no contiene registros."
}

$requiredColumns = @("ENTIDAD", "TIPO DE ENTIDAD", "MES", "AÑO")
$actualColumns = @($rows[0].PSObject.Properties.Name)

foreach ($column in $requiredColumns) {
    if ($column -notin $actualColumns) {
        throw "Falta la columna obligatoria: $column"
    }
}

$validRows = @(
    $rows | Where-Object {
        -not [string]::IsNullOrWhiteSpace($_.ENTIDAD) -and
        -not [string]::IsNullOrWhiteSpace($_."TIPO DE ENTIDAD") -and
        -not [string]::IsNullOrWhiteSpace($_.MES) -and
        -not [string]::IsNullOrWhiteSpace($_."AÑO")
    }
)

$invalidRows = $rows.Count - $validRows.Count

$processed = @(
    $validRows |
        Group-Object {
            "$($_.ENTIDAD.Trim())|$($_.'TIPO DE ENTIDAD'.Trim())|$($_.MES.Trim())|$($_.'AÑO'.Trim())"
        } |
        ForEach-Object {
            $first = $_.Group[0]
            $month = $first.MES.Trim().ToUpperInvariant()
            $yearValue = [double]$first.AÑO.Trim()

            if ($month -notin $MonthNumbers.Keys) {
                throw "Mes no reconocido: $month"
            }

            if ($yearValue -ne [math]::Truncate($yearValue)) {
                throw "Año inválido: $($first.AÑO)"
            }

            [PSCustomObject]@{
                Entity = $first.ENTIDAD.Trim()
                Category = $first."TIPO DE ENTIDAD".Trim()
                Month = $month
                Year = [int]$yearValue
                SourceOccurrences = $_.Count
            }
        }
)

$history = @(
    $processed |
        Sort-Object Year,
            @{ Expression = { $MonthNumbers[$_.Month] } },
            Category, Entity
)

$history | Export-Csv -Path $HistoryFile -NoTypeInformation -Encoding UTF8

Write-Host "Construyendo catálogo institucional..."

$normalizedHistory = foreach ($record in $history) {
    [PSCustomObject]@{
        Entity = $record.Entity
        Category = $record.Category
        NormalizedName = Normalize-Text $record.Entity
        NormalizedCategory = Normalize-Text $record.Category
        Year = [int]$record.Year
        Month = $record.Month
        MonthNumber = $MonthNumbers[$record.Month]
        SourceOccurrences = [int]$record.SourceOccurrences
    }
}

$institutions = @(
    foreach ($group in ($normalizedHistory |
        Group-Object NormalizedName, NormalizedCategory)) {

        $records = @($group.Group)

        $first = $records |
            Sort-Object Year, MonthNumber |
            Select-Object -First 1

        $last = $records |
            Sort-Object Year, MonthNumber |
            Select-Object -Last 1

        $nameVariants = @(
            $records |
                Group-Object -Property Entity -CaseSensitive |
                Sort-Object @{
                    Expression = {
                        ($_.Group |
                            Measure-Object SourceOccurrences -Sum).Sum
                    }
                    Descending = $true
                }, Name
        )

        $sourceNames = @(
            $nameVariants | Select-Object -ExpandProperty Name
        )

        $identityKey = "$($records[0].NormalizedCategory)|$($records[0].NormalizedName)"

        [PSCustomObject]@{
            id = Get-StableId $identityKey
            name = $nameVariants[0].Name
            normalizedName = $records[0].NormalizedName
            category = $records[0].Category
            sourceNames = $sourceNames
            firstObserved = "{0}-{1:D2}" -f $first.Year, $first.MonthNumber
            lastObserved = "{0}-{1:D2}" -f $last.Year, $last.MonthNumber
            observationCount = (
                $records |
                    Measure-Object SourceOccurrences -Sum
            ).Sum
        }
    }
)

$duplicateIds = @(
    $institutions |
        Group-Object id |
        Where-Object Count -gt 1
)

if ($duplicateIds.Count -gt 0) {
    throw "Se detectaron identificadores duplicados."
}

$totalOccurrences = (
    $history |
        Measure-Object SourceOccurrences -Sum
).Sum

if ($totalOccurrences -ne $validRows.Count) {
    throw "La suma de observaciones no coincide con los registros válidos."
}

$catalog = [PSCustomObject]@{
    schemaVersion = "1.0.0"
    dataset = "SB Institutional Registry"
    source = "Superintendencia de Bancos de la República Dominicana"
    sourceFile = "sb-authorized-entities-2018-2026.csv"
    generatedFrom = "sb-institutional-history.csv"
    identityPolicy = "Normalized source name and category; not a verified legal identity."
    institutions = @($institutions | Sort-Object category, name)
}

$catalog |
    ConvertTo-Json -Depth 8 |
    Set-Content -Path $CatalogFile -Encoding UTF8

Write-Host "Actualizando manifiesto de procedencia..."

$ManifestFile = Join-Path $Root "data/registry/source-manifest.json"

if (-not (Test-Path $ManifestFile)) {
    throw "No se encontró el manifiesto: $ManifestFile"
}

$manifest = Get-Content $ManifestFile -Raw | ConvertFrom-Json

$sourceInfo = Get-Item $SourceFile
$historyInfo = Get-Item $HistoryFile
$catalogInfo = Get-Item $CatalogFile
$scriptInfo = Get-Item $PSCommandPath

# Refresh source provenance from the exact file used in this execution.
$manifest.sourceFileSizeBytes = $sourceInfo.Length
$manifest.sourceSha256 = (Get-FileHash $SourceFile -Algorithm SHA256).Hash

$manifest.processing.originalRows = $rows.Count
$manifest.processing.validRows = $validRows.Count
$manifest.processing.excludedRows = $invalidRows
$manifest.processing.processedHistoryRecords = $history.Count
$manifest.processing.preservedObservations = $totalOccurrences
$manifest.processing.duplicateRowsConsolidated = $validRows.Count - $history.Count
$manifest.processing.nominalIdentities = $institutions.Count

$manifest | Add-Member -MemberType NoteProperty -Name artifacts -Value $null -Force
$manifest | Add-Member -MemberType NoteProperty -Name generator -Value $null -Force
$manifest | Add-Member -MemberType NoteProperty -Name generation -Value $null -Force

$manifest.artifacts = [PSCustomObject]@{
    history = [PSCustomObject]@{
        file = $manifest.processing.historyFile
        sizeBytes = $historyInfo.Length
        sha256 = (Get-FileHash $HistoryFile -Algorithm SHA256).Hash
    }
    catalog = [PSCustomObject]@{
        file = $manifest.processing.catalogFile
        sizeBytes = $catalogInfo.Length
        sha256 = (Get-FileHash $CatalogFile -Algorithm SHA256).Hash
    }
}

$manifest.generator = [PSCustomObject]@{
    file = $manifest.processing.script
    sha256 = (Get-FileHash $scriptInfo.FullName -Algorithm SHA256).Hash
}

$manifest.generation = [PSCustomObject]@{
    generatedAt = [DateTime]::UtcNow.ToString("yyyy-MM-ddTHH:mm:ssZ")
    environment = "PowerShell"
}

$manifestTempFile = "$ManifestFile.tmp"

$manifest |
    ConvertTo-Json -Depth 12 |
    Set-Content -Path $manifestTempFile -Encoding UTF8

# Validate the serialized manifest before replacing the current file.
$null = Get-Content $manifestTempFile -Raw | ConvertFrom-Json

Move-Item -Path $manifestTempFile -Destination $ManifestFile -Force

Write-Host ""
Write-Host "GENERACIÓN COMPLETADA"
Write-Host "Registros originales: $($rows.Count)"
Write-Host "Registros válidos: $($validRows.Count)"
Write-Host "Filas no institucionales o incompletas: $invalidRows"
Write-Host "Registros históricos procesados: $($history.Count)"
Write-Host "Observaciones conservadas: $totalOccurrences"
Write-Host "Identidades nominales: $($institutions.Count)"
Write-Host "IDs duplicados: $($duplicateIds.Count)"
Write-Host "Histórico: $HistoryFile"
Write-Host "Catálogo: $CatalogFile"
