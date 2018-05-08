SETLOCAL enableextensions
mkdir node_modules
compact /c /i /q /f /s:node_modules
npm set progress=false
npm install --skip-installed --cache-min 999999