$files = Get-ChildItem -Path "d:\m1 java\src\main\webapp\WEB-INF\views" -Filter *.jsp
foreach ($f in $files) {
    $content = Get-Content $f.FullName -Raw
    
    # 1. CSS Link
    $content = $content -replace '<link rel="stylesheet" href="\$\{pageContext\.request\.contextPath\}/css/styles\.css[^>]*>', '<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">'
    
    # 2. Navbar fixes
    $content = $content -replace 'class="navbar"', 'class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 px-3"'
    $content = $content -replace 'class="navbar-nav"', 'class="navbar-nav ms-auto flex-row gap-3"'
    $content = $content -replace 'class="navbar-brand"', 'class="navbar-brand fw-bold"'
    
    # 3. Layout classes
    $content = $content -replace 'class="form-grid"', 'class="row g-3"'
    $content = $content -replace 'class="form-group full-width"', 'class="col-12 mb-3"'
    $content = $content -replace 'class="form-group"', 'class="col-md-6 mb-3"'
    
    # 4. Form inputs (Regex carefully targeting tags without class yet)
    $content = $content -replace '<input type="text" (id=[^>]+)>', '<input type="text" class="form-control" $1>'
    $content = $content -replace '<input type="email" (id=[^>]+)>', '<input type="email" class="form-control" $1>'
    $content = $content -replace '<input type="number" (id=[^>]+)>', '<input type="number" class="form-control" $1>'
    $content = $content -replace '<input type="datetime-local" (id=[^>]+)>', '<input type="datetime-local" class="form-control" $1>'
    $content = $content -replace '<select (id=[^>]+)>', '<select class="form-select" $1>'
    $content = $content -replace '<textarea (id=[^>]+)', '<textarea class="form-control" $1'
    
    # 5. Labels
    $content = $content -replace '<label for', '<label class="form-label" for'
    
    # 6. Tables
    $content = $content -replace '<table>', '<table class="table table-bordered table-striped table-hover mt-3">'
    
    # 7. Add JS before body end
    $content = $content -replace '</body>', '<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>`n</body>'

    Set-Content -Path $f.FullName -Value $content -Encoding UTF8
}
