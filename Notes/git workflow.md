# git workflow

1. 切到本地 main
git switch main

2. 把远程 main 最新代码同步下来
git pull origin main

3. 切回你的开发 branch
git switch andy-dev

4. 把最新 main 合进 andy-dev
git merge main

5. 我自己的local branch 开发
git add .
git commit -m "Implement xxx"

6. 最后push 到remote 自己的branch
git push origin andy-dev

7. 从remote自己的branch PR + code review 到remote main

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

# undo
`git reset --soft HEAD~1`:
- git: run Git.
- reset: move your current branch to a specified commit.
- --soft: keep both your files and the staging area unchanged.
- HEAD: the commit you’re currently on.
- ~1: go back one generation—to that commit’s first parent.

| Before                                  | After                                     |
| --------------------------------------- | ----------------------------------------- |
| Your branch points to the latest commit | Your branch points to the previous commit |
| Changes are committed                   | Those changes remain staged               |
| Files contain your code                 | Files still contain the same code         |
