import tkinter
import tkinter.ttk as ttk #tk의 확장 (트리뷰, 콤보박스등 사용가능)
from tkinter import *
import csv
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from PIL import ImageTk, Image #배경 이미지 쓰기위한 모듈

result_list=[]
def crl():
    tex_box.delete(*tex_box.get_children())
    result_list.clear()
    e=findReplace()
    find=e
    options=webdriver.ChromeOptions()
    options.add_argument('chromedriver.exe')
    options.add_experimental_option('detach',True)
    driver=webdriver.Chrome(options=options)
    driver.implicitly_wait(10)
    res=driver.get('https://map.naver.com/v5/?c=11.78,0,0,0,dh')

    print('들어갔디')
    infoli=[]#장소정보
    info2li=[]#주소
    placeli=[]#장소이름
    want=[]#즐겨찾기
    while True:
        print('와일문들어왔디')
        search_box = driver.find_element(By.CLASS_NAME, "input_search")
        search_box.send_keys(find)
        search_box.send_keys(Keys.ENTER)
        print('키값 들어감')
        print('프레임교체한다')
        driver.switch_to.frame(driver.find_element('id','searchIframe'))
        print('프레임 교체까지 된거같은데?')
        print('-' * 30)
        #info=driver.find_elements(By.CLASS_NAME,'YzBgS')
        info = driver.find_elements(By.CLASS_NAME, 'KCMnt')
        #info = driver.find_elements(By.CLASS_NAME, 'place_bluelink.TYaxT')
        #info2 = driver.find_elements(By.CLASS_NAME, 'hClKF')
        #info2 = driver.find_elements(By.CLASS_NAME, 'KcMnt')
        info2 = driver.find_elements(By.CLASS_NAME, 'place_bluelink.TYaxT')
        #place= driver.find_elements(By.CLASS_NAME, 'place_bluelink.YwYLL')
        place = driver.find_elements(By.CLASS_NAME, 'place_bluelink.TYaxT')

        for i in (info):
            infoli.append(i)
        for i in info2:
            info2li.append(i)
        for i in (place):
            placeli.append(i)
        for i,j,k in zip(infoli,placeli,info2li):
            result=(j.text,i.text,k.text)
            result_list.append(result)

        print(type(result_list))
        for i in result_list:
            print(result_list.index(i)+1,i)
        print('-'*30)
        for i in result_list:
            print(i[0],i[1],i[2])
        break

w=Tk() #Tk함수 사용
def select(): #리스트에서 정보 가져옴
        tex_box.delete(*tex_box.get_children())
        for row in result_list:  # csv에서 가져온 리스트를 출력
            #tex_box.insert('', 'end', values=row)# 가져온 리스트에서 하나씩 리스트박스(gui)에 추가시킴
            tex_box.insert('', 'end', values=row)
def findReplace():
    return name.get()
    print('--')
def click(event): #클릭시 발생하는 함수
    selected_item = tex_box.focus()
    getValue = tex_box.item(selected_item).get('values') #클릭한 정보를 저장
def insert_fav(): #찜리스트에 담은 정보를 보여주는 함수
    selected_item = tex_box.focus()
    getValue = tex_box.item(selected_item).get('values')  # 클릭한 정보를 저장
    tex_box2.insert('','end',values=getValue, iid=getValue) #iid값으로 수정 및 삭제가능
def delete_fav(): #찜리스트 항목 삭제
    selected_item = tex_box2.focus()
    getValue = tex_box2.item(selected_item).get('values')  # 클릭한 정보를 저장
    if selected_item:
        print(getValue)
        tex_box2.delete(getValue)
def savePl():#csv에 즐겨찾기 저장
    selected_item = tex_box2.focus()
    getValue = tex_box2.item(selected_item).get('values')
    if(getValue!=''):
        f = open('c:/dir/trav34.csv', 'a+', newline='')
        writer=csv.writer(f)
        writer.writerow(getValue)#수정해야함
        f.close
    else:
        return False
def listSelect():
    fileName = "c:/dir/trav34.csv"
    file = open(fileName, 'r')
    reader = csv.reader(file)
    arr = []  # 즐겨찾기 리스트선언
    for row in reader:
        arr.append(row)  # reader 값들 arr에 저장
    tex_box3.delete(*tex_box3.get_children())
    for row in arr:  # csv에서 가져온 리스트를 출력
        #tex_box.insert('', 'end', values=row)# 가져온 리스트에서 하나씩 리스트박스(gui)에 추가시킴
        tex_box3.insert('', 'end', values=row)
        print(row)
#----------- gui 화면  ---------------

w.title('프로젝트 여행')  # gui상단 타이틀 이름
w.resizable(0, 0)  # gui화면 크기조절 가능여부
w.geometry('1200x800+500+300')  # gui화면 크기 설정

#--배경이미지
background_img = Image.open("budapest.png").resize((1200, 800)) #사진 크기 조절
background_tkimg = ImageTk.PhotoImage(background_img) #사진불러오기

canv = tkinter.Canvas(w)
canv.create_image(0, 0, image=background_tkimg, anchor="nw")
canv.place(x=0, y=0, relwidth=1, relheight=1)


# --장소검색 프레임,라벨,버튼
la1=Label(w,text='장소명',font=('',13),bg='white').place(x=208, y=20)  # 장소입력 글자 표시
name = StringVar()
et1 = Entry(w,textvariable=name,font=('',12),width=19).place(x=270, y=21)  # 장소입력창
bu12 = Button(w,text='검색',font=('',12),command=crl).place(x=432, y=20)#크롤링 단어 검색버튼
bu1 = Button(w,text='조회',font=('',12),command=select).place(x=485, y=20)  #크롤링 조회버튼


# 웹크롤링 리스트를 보여주는 창
tex_box = ttk.Treeview(w,columns=(0,1,2), height=5, show='headings' )
tex_box.place(x=70, y=50)

# --즐겨찾기 프레임,라벨,버튼
la_bookmark = Label(w,text='즐겨찾기',font=('',13),bg='white').place(x=230, y=215)
bu2 = Button(w,text='추가',font=('',12),command=insert_fav).place(x=320, y=215) #찜리스트항목 추가버튼
bu3 = Button(w,text='삭제',font=('',12),command=delete_fav).place(x=380, y=215) #찜리스트항목 삭제버튼
bu4 = Button(w,text='CSV저장',font=('',12),command=savePl).place(x=440, y=215)

# --즐겨찾기 창
tex_box2 = ttk.Treeview(w,columns=(0,1,2), height=5, show='headings') #즐겨찾기를 보여주는 창
tex_box2.place(x=70, y=250) #화면에 표시

# --CSV 파일 불러오기 라벨,버튼
la_save=Label(w,text='CSV',font=('',13),bg='white').place(x=320, y=421)
bu5 = Button(w, text='조회', command=listSelect,font=('', 12)).place(x=370, y=420)

# --CSV 파일 불러오기 창
tex_box3 = ttk.Treeview(w,columns=(0,1,2), height=15, show='headings')
tex_box3.place(x=70, y=450) #화면에 표시

# 정보를 리스트칸에 칼럼에 맞게 나열
tex_box2.heading(0)
tex_box2.heading(1)
tex_box2.heading(2)

# 칼럼 칸크기 설정
tex_box2.column(0)
tex_box2.column(1)
tex_box2.column(2)

tex_box.bind('<ButtonRelease-1>', click)  # 클릭기능 생성
w.mainloop()  # gui화면 띄움