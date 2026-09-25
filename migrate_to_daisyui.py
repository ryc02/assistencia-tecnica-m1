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
    
    # 3. Add html data-theme
    content = content.replace('<html lang="pt-BR">', '<html lang="pt-BR" data-theme="corporate">')
    
    # 4. DaisyUI & Tailwind links
    daisy_links = '<link href="https://cdn.jsdelivr.net/npm/daisyui@4.10.1/dist/full.min.css" rel="stylesheet" type="text/css" />\n    <script src="https://cdn.tailwindcss.com"></script>'
    content = re.sub(r'<link rel="stylesheet" href="\$\{pageContext\.request\.contextPath\}/css/styles\.css[^>]*>', daisy_links, content)
    
    # 5. DaisyUI classes
    content = content.replace('class="navbar"', 'class="navbar bg-base-200 shadow-md px-6"')
    content = content.replace('class="navbar-nav"', 'class="menu menu-horizontal px-1 gap-2"')
    content = content.replace('class="navbar-brand"', 'class="btn btn-ghost text-xl"')
    
    content = content.replace('class="container"', 'class="container mx-auto p-4 max-w-6xl mt-8"')
    content = content.replace('class="card"', 'class="card bg-base-100 shadow-xl border border-base-300 mb-8"')
    content = content.replace('class="card-title"', 'class="card-title text-2xl mb-6 border-b pb-4 px-6 pt-6"')
    
    # Wrap form grid correctly (JSP might have raw forms inside cards without card-body)
    content = content.replace('<form', '<div class="card-body pt-0">\n        <form')
    content = content.replace('</form>', '</form>\n        </div>')
    
    content = content.replace('class="table-responsive"', 'class="overflow-x-auto card-body pt-0"')
    
    content = content.replace('class="form-grid"', 'class="grid grid-cols-1 md:grid-cols-2 gap-6"')
    content = content.replace('class="form-group full-width"', 'class="form-control w-full col-span-full"')
    content = content.replace('class="form-group"', 'class="form-control w-full"')
    
    # Inputs
    content = re.sub(r'<input type="text" (id=[^>]+)>', r'<input type="text" class="input input-bordered w-full" \1>', content)
    content = re.sub(r'<input type="email" (id=[^>]+)>', r'<input type="email" class="input input-bordered w-full" \1>', content)
    content = re.sub(r'<input type="number" (id=[^>]+)>', r'<input type="number" class="input input-bordered w-full" \1>', content)
    content = re.sub(r'<input type="datetime-local" (id=[^>]+)>', r'<input type="datetime-local" class="input input-bordered w-full" \1>', content)
    content = re.sub(r'<select (id=[^>]+)>', r'<select class="select select-bordered w-full" \1>', content)
    content = re.sub(r'<textarea (id=[^>]+)', r'<textarea class="textarea textarea-bordered w-full h-32" \1', content)
    
    # Labels
    content = content.replace('<label for', '<label class="label"><span class="label-text font-bold" for')
    content = content.replace('</label>', '</span></label>')
    
    # Tables
    content = content.replace('<table>', '<table class="table table-zebra w-full text-base">')
    content = content.replace('<thead>', '<thead class="bg-base-200 text-base-content text-sm">')

    # Badges (replace existing badge logic if any)
    content = content.replace('badge-pendente', 'badge badge-warning')
    content = content.replace('badge-aprovado', 'badge badge-success')
    content = content.replace('badge-recusado', 'badge badge-error')
    content = content.replace('badge-aberta', 'badge badge-info')
    content = content.replace('badge-em_andamento', 'badge badge-warning')
    content = content.replace('badge-concluida', 'badge badge-success')
    content = content.replace('badge-cancelada', 'badge badge-ghost')
    
    # Buttons (btn btn-primary is already compatible with daisyui, just needs to ensure it's there)

    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
