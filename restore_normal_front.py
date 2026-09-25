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
    
    # 3. Bump CSS cache to v=5
    content = re.sub(r'<link rel="stylesheet" href="\$\{pageContext\.request\.contextPath\}/css/styles\.css[^>]*>', 
                     '<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=5">', content)

    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
