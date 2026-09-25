import os
import re

views_dir = r"d:\m1 java\src\main\webapp\WEB-INF\views"

for filename in os.listdir(views_dir):
    if not filename.endswith(".jsp"): continue
    path = os.path.join(views_dir, filename)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()
    
    # 1. Fix eq to equip
    content = content.replace('var="eq"', 'var="equip"')
    content = content.replace('${eq.', '${equip.')
    
    # 2. Remove emojis
    content = re.sub(r'[➕📋👤💻💰🛠️🔗📝💾✅❌🗑️🔍▶️⚙️]', '', content)
    
    # 3. Bootstrap link
    content = re.sub(r'<link rel="stylesheet" href="\$\{pageContext\.request\.contextPath\}/css/styles\.css[^>]*>', 
                     '<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">', content)
    
    # 4. Bootstrap classes
    content = content.replace('class="navbar"', 'class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 px-3"')
    content = content.replace('class="navbar-nav"', 'class="navbar-nav ms-auto flex-row gap-3"')
    content = content.replace('class="navbar-brand"', 'class="navbar-brand fw-bold text-white text-decoration-none"')
    
    content = content.replace('class="form-grid"', 'class="row g-3"')
    content = content.replace('class="form-group full-width"', 'class="col-12 mb-3"')
    content = content.replace('class="form-group"', 'class="col-md-6 mb-3"')
    
    content = re.sub(r'<input type="text" (id=[^>]+)>', r'<input type="text" class="form-control" \1>', content)
    content = re.sub(r'<input type="email" (id=[^>]+)>', r'<input type="email" class="form-control" \1>', content)
    content = re.sub(r'<input type="number" (id=[^>]+)>', r'<input type="number" class="form-control" \1>', content)
    content = re.sub(r'<input type="datetime-local" (id=[^>]+)>', r'<input type="datetime-local" class="form-control" \1>', content)
    content = re.sub(r'<select (id=[^>]+)>', r'<select class="form-select" \1>', content)
    content = re.sub(r'<textarea (id=[^>]+)', r'<textarea class="form-control" \1', content)
    
    content = content.replace('<label for', '<label class="form-label fw-bold" for')
    content = content.replace('<table>', '<table class="table table-bordered table-striped table-hover mt-3">')
    
    content = content.replace('</body>', '<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>\n</body>')

    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
