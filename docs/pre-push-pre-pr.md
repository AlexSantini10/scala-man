# Pre-push and pre-PR checks

Checklist for what to verify before publishing changes.

## Before push

1. Confirm the project version matches what is expected in [`build.sbt`](../build.sbt).
2. Run the basic local checks:
   - `sbt compile test scalafmtCheckAll`
3. If you changed packaging, also verify the assembly:
   - `sbt assembly`
4. Check that there are no generated files or unwanted diff changes.

## Before PR

1. Repeat the checks above if you changed code after the last push.
2. Make sure CI matches what you tested locally:
   - the test workflow runs `compile test scalafmtCheckAll` in [`.github/workflows/ci.yml`](../.github/workflows/ci.yml)
   - the delivery workflow runs `compile test`, `assembly`, and builds the package in [`.github/workflows/cd.yml`](../.github/workflows/cd.yml)
3. Remember that the version is not computed automatically by the pipeline:
   - today it comes from `ThisBuild / version := "0.1.0-SNAPSHOT"` in [`build.sbt`](../build.sbt)
4. If the change affects the release, update the related documentation before opening the PR.

## Note

The GitHub release published by CD uses the fixed `latest` tag, so it is not an automatic SemVer version of the software.
