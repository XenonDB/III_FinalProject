use HealthyLifestyle;


/*

資料表結構：
分兩類為主：實體物件(例如工作人員、醫師、諮詢者、商品描述...等可以對應到個體的資料表)和事件(交易紀錄、諮詢紀錄、登入登出紀錄...等以「物件的行為」為主的資料表)

記錄實體物件類的資料表：
1.成員(含員工、醫師、諮詢者。)
成員資料至少包含：id、名稱
成員可能含法人
登入帳號使用郵件，密碼部分則以雜湊函數取摘要後紀錄(所以就算有人能翻出資料庫內容也很難知道具體密碼，除非能動手修改資料庫)
若有一個成員的密碼欄位為null，則表示該成員僅作為資料庫內部紀錄用，不可用於實際登錄。(例如：公司本身)
1.1 醫師資訊表(至少含專長，其ID對應到一個註冊在此的唯一一個成員，表示該筆醫師資訊是屬於該成員的)
1.2 員工資訊表(至少含職位，其ID對應到一個註冊在此的唯一一個成員，表示該筆員工資訊是屬於該成員的)

2.商品(還不確定要不要賣東西。但商品列表裡會包含「問診」，即是將問診行為也視為商品。)

記錄事件類的資料表：
1.交易記錄(含問診記錄、商品購買記錄(視為同一種資料，因為問診也被視為商品)。資料表內容包含：交易物品、交易預約中(問診預約or預約商品)、交易中(=問診中)、交易完畢，以及評價。)
交易物品包含「薪水」等用於內部紀錄的資料(例如：會有一筆交易物品為「薪水」，定價為0元。該物品將用於「交易紀錄」中發給員工或醫師的薪水、費用所使用)

2.登入記錄(含登入IP、登入時間(以SQL伺服器所在位置為準)、登入時使用者所在地區(不確定能不能知道))。

*/


create table [Member](
  [user] varchar(128) not null primary key,
  mail varchar(128),
  lastName nvarchar(50), --姓氏
  firstName nvarchar(50), --名
  hashedPassword nvarchar(64),
  gender nvarchar(32),
  bloodtypeABO nvarchar(32),
  birthday date,
  photo varchar(max),--個人資料的頭像，以javascript的FileReader轉換成的base64字串記錄(data:image/png;base64,...)
  phone nvarchar(32),
  height decimal(8,2), --身高
  [weight] decimal(8,2), --體重
  city nvarchar(8), --居住城市
  [location] nvarchar(128), --居住地點(不含城市)
  [desc] nvarchar(1024)--個人資料的描述(健康存摺用)
)

--語言列表以語系/地區碼表示，可用的地區碼請參考healthylifestyle.utils.Language
--表示一個使用者會哪些語言。一個人可能會多種不同語言
create table [AvailableLanguage](
	[user] varchar(128) not null foreign key references [Member]([user]),
	[language] varchar(8) not null
)

create table [EnumSocialMedia](
	id int not null primary key identity(1,1),
	[name] varchar(128) not null
)

insert into [EnumSocialMedia]([name]) values('Facebook');
insert into [EnumSocialMedia]([name]) values('Line');
insert into [EnumSocialMedia]([name]) values('Google');

create table [SocialMediaAccounts](
	[user] varchar(128) not null foreign key references [Member]([user]),
	socialMediaId int not null foreign key references [EnumSocialMedia](id),
	account varchar(128) not null
)

create table [Doctors](
  [user] varchar(128) not null primary key foreign key references [Member]([user]),
  profession nvarchar(256) not null
);

create table [OfficeLevel](
  [level] int not null primary key,
  [name] nvarchar(256)
);
--注意!! 無任何特殊權限(權限0或null)將不紀錄於此。
insert into [OfficeLevel]([level],name) values(100,'系統管理員');
insert into [OfficeLevel]([level],name) values(1,'一般員工');

create table [Employees](
  [user] varchar(128) not null primary key foreign key references [Member]([user]),
  maxOfficeLevel int foreign key references [OfficeLevel]([level]),--員工專屬，表示這名員工可動用的最高職位or權限等級。不存在於此表紀錄的人預設為0，表示非員工。
);

insert into [Member]([user],firstName) values('System','系統帳號');
insert into [Employees] values('System',100);

insert into [Member](firstName,[user],hashedPassword) values('測試管理員帳號','Admin','$2a$10$Cs4flB8Wy.2euaXb8dkMROClq9ptGGA7vqGBGIwcf/GyvpVYCgR1a');-- 預設密碼 healthy
insert into [Employees] values('Admin',100);

insert into [Member] values('RRR','RRR@RRR.com','Lee','Long','$2a$10$ggFu4s4foKvzQ6AY/TAFDu4bMO11VEQaSyYAg1POktA7bnCGyjWN6','MALE','O','2022-11-6',null,'0987848763',199.1,66.2,'高雄市','新西區',null);--預設密碼 rrr
insert into [AvailableLanguage] values('RRR','zh_tw');
insert into [AvailableLanguage] values('RRR','ja_jp');
--insert into [AvailableLanguage] values('RRR','ja_jpp');

insert into [Member] values('Lai','lai@lai.com','賴','普拿疼-潘志遠','$2a$10$UnA66biPUgb9PDsSajsKEeBdjvyarDKhqrDb55eSNRLLPx1rwhbTy','MALE','O','2022-11-6',null,'0987848763',189.7,56.2,'台北市','信義區',null);--預設密碼 lai
insert into [Doctors] values('Lai','神經內科')

create table [UserSchedule](
	[user] varchar(128) not null foreign key references [Member]([user]),
	[date] date,
	title nvarchar(32),
	theme varchar(16)
)

insert into [UserSchedule] values('RRR','2021-08-09','YAA','green');
insert into [UserSchedule] values('RRR','2022-07-11','2ㄏ','yellow');

create table [DiagnosisBooking](
	[user] varchar(128) not null foreign key references [Member]([user]),
	[date] datetime not null,
	interval int not null,
	[doctor] varchar(128) not null foreign key references [Doctors]([user]),
	[diagClass] nvarchar(32) not null,
	[desc] nvarchar(1024)
)

insert into [DiagnosisBooking] values('RRR','2022-07-11 08:47:03',1800,'Lai','神經內科','脖子涼涼的，感覺有東西勒著。');

create table [LoginRecord](
  id int not null primary key identity(1,1),
  loginMember varchar(128) not null foreign key references [Member]([user]),
  ip varchar(64) not null,--可記錄ipv4或ipv6位址
  date datetimeoffset not null default SYSDATETIMEOFFSET()
)

create table [Transaction_status](
  id int not null primary key,
  name nvarchar(64) not null,
);

create table [Products](
  pid int not null primary key identity(1,1),--商品ID
  ppic varchar(max),--商品照片，以html img可以使用的base64直接儲存(data:image/png;base64,...)
  pname nvarchar(128) not null,--商品名稱
  price int not null, -- 定價，單位為新台幣，但不一定會在交易時使用。
  qty int not null,--該商品剩餘數量
  seller varchar(128) not null foreign key references [Member]([user])--販售該商品的會員是誰
);

insert into [Products] values(null,'維維骨力',30,300,'System');
insert into [Products] values(null,'普拿疼疼',60,200,'System');

create table [Transaction](
  oid int not null primary key identity(1,1),--訂單ID
  pid int not null foreign key references [Products]([pid]),--交易商品ID
  qty int not null,--交易商品的數量
  seller varchar(128) not null foreign key references [Member]([user]),--販售者
  customer varchar(128) not null foreign key references [Member]([user]),--購買者
  price int not null, --此次交易(預計或已完成)的成交價格。
  seller_comment nvarchar(1024),--販售者對此次交易的評價。
  customer_comment nvarchar(1024),--購買者對此次交易的評價。
  [status] int not null foreign key references [Transaction_status](id), --交易狀態
  odate datetimeoffset not null default SYSDATETIMEOFFSET(), --訂單產生的日期
  postal_code int, --交易物品欲送達地址的郵遞區號。只有在地址存在的狀況下才可能存在。
  location_desc nvarchar(1024) --交易物品欲送達的實體地址。實體地址的意義為給customer的取貨位址。
);

insert into Transaction_status values(0,'交易預定中');
insert into Transaction_status values(1,'交易進行中');
insert into Transaction_status values(2,'交易完成');

--表示一個成員可以看到那些聊天群組的紀錄。此表預設適用於權限0的成員。擁有更高階權限的人則視情況處裡。
create table [ChattingGroupJoinList](
	[user] varchar(128) not null foreign key references [Member]([user]),
	[group] int not null
);

--僅用於後台調閱聊天紀錄用。即時聊天時，對話內容將直接傳送給聊天對象，並額外儲存於此。
--例：insert into [ChattingRecord]([group],sender,message) values('1;2',1,'abc');
create table [ChattingRecord](
  id int not null primary key identity(1,1),
  [group] int not null, --group表示聊天群組編號。一條訊息只有在同一個群組內的成員才能看到。
  sender varchar(128) not null foreign key references [Member]([user]), --發送該條訊息的人是誰
  message nvarchar(2048), --傳送的特定一條訊息
  date datetimeoffset not null default SYSDATETIMEOFFSET()
);
