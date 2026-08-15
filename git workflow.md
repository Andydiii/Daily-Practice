# 1. 切到本地 main
git switch main

# 2. 把远程 main 最新代码同步下来
git pull origin main

# 3. 切回你的开发 branch
git switch andy-dev

# 4. 把最新 main 合进 andy-dev
git merge main
