import tabula
import pandas as pd
import numpy as np
import calendar
import pymysql

# Read pdf into DataFrame
#df = tabula.read_pdf("/root/youngeun/menufiles/201907.pdf")

#print(df)

daysSample=["월","화","수","목","금","토","일"]

conn=pymysql.connect(host='localhost',user='suram',password='suram',db='tidsnotedb' ,charset='utf8')
print("connetion success");
curs=conn.cursor()

sql="select file_name from upload where rcv_python='0';"
curs.execute(sql)
row=curs.fetchone()
print(row)

file_name=str(row[0])


month=file_name[4:]
year=file_name[0:4]
print(calendar.monthrange(int(year),int(month))[1])
lastDay=calendar.monthrange(int(year),int(month))[1]
startDay=calendar.weekday(int(year),int(month),1)
startDayInt=1
countDayint=1
#매월 시작요일(주말 제외) int형
while(startDay>4):
    startDay+=1
    if(startDay==7):
        startDay=0
    startDayInt+=1 #시작일
    countDayint=startDayInt


#시작요일 한글로 변환
startDay=daysSample[startDay]
print(startDay)

# Read remote pdf into DataFrame
file_path="/root/suram/menufiles/"+file_name+".pdf"
df = tabula.read_pdf(file_path,encoding='utf-8')
#print(df)
days=["월","화","수","목","금"]
dayList=[]
monthList=[]
#print(df)
#print(startDayInt)
#print(len(df.index))
for j in range(0, 5):
    if(j!=0):
        startDay=int(days.index(startDay))
        if(startDay==4):
            print("ok")
            startDay=0
            countDayint+=3
            startDayInt=countDayint
            print(countDayint)
            print(startDayInt)
        else:
            startDay+=1
            countDayint+=1
            startDayInt=countDayint
            print(countDayint)
            print(startDayInt)

        startDay=days[startDay]
        print(startDay)

    for i in range(0, len(df.index)):
        target = str(df.loc[i, startDay])

        # 해당 요일의 날짜가 나오면 기존리스트 저장 및 초기화 후 생성시작
        if (target == str(startDayInt)):
            print(startDayInt)
            if (startDayInt < 10):
                dayList.append(year + "-" + month + "-0" + str(startDayInt))
            else:
                dayList.append(year + "-" + month + "-" + str(startDayInt))
            startDayInt += 7

        # 리스트에 메뉴담기 시작
        else:
            idx = str(df.loc[i, "구분"])
            # print(idx)
            if ('오전간식' in idx):
                pass
            elif ('오후간식' in idx):
                if dayList:
                    monthList.append(dayList)
                    dayList = []
                else:
                    dayList = []
            elif ('열량/단백질' in idx):
                pass
            elif ('점심식사' or 'nan' in idx):
                if (str(df.loc[i, startDay]) in 'nan'):
                    pass
                else:
                    dayList.append(df.loc[i, startDay])



#print(monthList)
monthList.sort()

sql="insert into menu_tbl(m_date,m_menu) values(%s,%s);"

menus=""
for m in range(0,len(monthList)):
    sql2=""
    for n in range(0,len(monthList[m])):
        if(n>0):
            menus=menus+monthList[m][n]+","
        else:
            menus=""
            m_date=monthList[m][n]
    menus=menus[:-1]        
    length=len(menus)
    if(length>0):
        curs.execute(sql,(m_date,menus))
        conn.commit()


sql2="update upload set rcv_python='1' where file_name='"+file_name+"';"
curs.execute(sql2);
conn.commit()

conn.close()
