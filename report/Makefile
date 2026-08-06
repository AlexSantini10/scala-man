.RECIPEPREFIX := >

PDF = report.pdf
BUILD_DIR = build
SOURCE = main.tex
LATEXMK_FLAGS = -pdf -interaction=nonstopmode -halt-on-error -file-line-error -jobname=report -outdir=$(BUILD_DIR)

.DEFAULT_GOAL := all
.PHONY: all clean

all: $(PDF)

$(PDF): $(SOURCE)
>latexmk $(LATEXMK_FLAGS) $(SOURCE)
>python -c "from pathlib import Path; import shutil, sys; build_pdf = Path('$(BUILD_DIR)/report.pdf'); report_pdf = Path('$(PDF)'); build_dir = Path('$(BUILD_DIR)'); report_pdf.unlink(missing_ok=True); shutil.copy2(build_pdf, report_pdf) if build_pdf.exists() else None; shutil.rmtree(build_dir, ignore_errors=True); sys.exit(1) if not report_pdf.exists() else None"

clean:
>latexmk -C $(LATEXMK_FLAGS) $(SOURCE)
>python -c "from pathlib import Path; import shutil; Path('$(PDF)').unlink(missing_ok=True); shutil.rmtree(Path('$(BUILD_DIR)'), ignore_errors=True); Path('main.pdf').unlink(missing_ok=True)"
