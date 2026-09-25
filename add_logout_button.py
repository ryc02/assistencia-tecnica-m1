import os
import re

views_dir = r"d:\m1 java\src\main\webapp\WEB-INF\views"

logout_html = """
    <div class="user-actions" style="display: flex; align-items: center; gap: 1rem; font-size: 0.875rem;">
        <span style="color: var(--muted-foreground);">Olá, <strong>${sessionScope.usuarioLogado}</strong></span>
        <a href="${pageContext.request.contextPath}/controle?acao=logout" style="color: hsl(0 84.2% 60.2%); text-decoration: none; font-weight: 500;">Sair</a>
    </div>
"""

for filename in os.listdir(views_dir):
    if not filename.endswith(".jsp") or filename == "login.jsp": continue
    path = os.path.join(views_dir, filename)
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()
    
    # Check if already added
    if "acao=logout" in content:
        continue
        
    # Inject after </ul> in navbar
    content = content.replace('</ul>\n</nav>', '</ul>\n' + logout_html + '</nav>')

    with open(path, "w", encoding="utf-8") as f:
        f.write(content)
