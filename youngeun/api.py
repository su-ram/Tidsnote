import requests
URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?ServiceKey=DkRPUL4om5zMDGR7jLAde9a%2Fsyg%2Bc%2FPKtilNgDwDjDZbJPzbuConyIOEYYZS96MjHndan5JKGCuZ8Kfm5HRyzw%3D%3D&_type=json&solYear=2019&solMonth=08"
html = requests.get(URL)
print(html.text)
