# 1. 切到本地 main
git switch main

# 2. 把远程 main 最新代码同步下来
git pull origin main

# 3. 切回你的开发 branch
git switch andy-dev

# 4. 把最新 main 合进 andy-dev
git merge main

# 5. 我自己的local branch 开发
git add .
git commit -m "Implement xxx"

# 6. 最后push 到remote 自己的branch
git push origin andy-dev

# 7. 从remote自己的branch PR + code review 到remote main

GitHub
remote main
   │
   │ git pull
   ↓
local main
   │
   │ git merge main
   ↓
local andy-dev
   │
   │ coding + commit
   │
   │ git push
   ↓
remote andy-dev
