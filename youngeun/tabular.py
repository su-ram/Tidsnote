import tabula
import pandas as pd
import numpy as np
import calendar
# Read pdf into DataFrame
#df = tabula.read_pdf("/root/youngeun/menufiles/201907.pdf")

#print(df)
print(calendar.monthrange(2019,8)[1])
lastDay=calendar.monthrange(2019,8)[1]


# Read remote pdf into DataFrame
df = tabula.read_pdf("/root/youngeun/menufiles/menu08.pdf",encoding='utf-8')
#print(df)
print (df.columns.values)

print("행데이터 가져오기")
print(df.iloc[0])
for(i=1;i<lastDay+1;i++){
        
        }
#df2.to_csv('output3.csv',encoding='utf-8')

#print(df2)
# convert PDF into CSV
#tabula.convert_into("/root/youngeun/menufiles/201908.pdf", "output.csv", output_format="csv", pages='all')

# convert all PDFs in a directory
#tabula.convert_into_by_batch("input_directory", output_format='csv', pages='all')


