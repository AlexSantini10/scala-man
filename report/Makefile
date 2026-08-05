.RECIPEPREFIX := >

PDF = report.pdf
SOURCE = main.tex
TEMP_PATTERNS_PY = ['*.aux', '*.log', '*.out', '*.toc', '*.fls', '*.fdb_latexmk', '*.synctex.gz', '*.nav', '*.snm', '*.lof', '*.lot']

.DEFAULT_GOAL := all
.PHONY: all clean postbuild

all: postbuild

$(PDF): $(SOURCE)
>-pdflatex -interaction=nonstopmode -halt-on-error -jobname=report $(SOURCE)
>-pdflatex -interaction=nonstopmode -halt-on-error -jobname=report $(SOURCE)
>python -c "from pathlib import Path; import shutil, sys; main = Path('main.pdf'); report = Path('report.pdf'); patterns = $(TEMP_PATTERNS_PY); sys.exit(1) if (not main.exists() and not report.exists()) else None; shutil.copy2(main, report) if main.exists() else None; [path.unlink(missing_ok=True) for pattern in patterns for path in Path('.').rglob(pattern)]; main.unlink(missing_ok=True)"

postbuild: $(PDF)
>python -c "from pathlib import Path; patterns = $(TEMP_PATTERNS_PY); [path.unlink(missing_ok=True) for pattern in patterns for path in Path('.').rglob(pattern)]; Path('main.pdf').unlink(missing_ok=True)"

clean:
>python -c "from pathlib import Path; targets = [Path('$(PDF)'), Path('main.pdf')]; patterns = $(TEMP_PATTERNS_PY); [path.unlink(missing_ok=True) for pattern in patterns for path in Path('.').rglob(pattern)]; [path.unlink(missing_ok=True) for path in targets]"
