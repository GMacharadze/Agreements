create table Info (
id nvarchar(10) not null primary key,
name nvarchar(max),
regnum nvarchar(max),
startdate nvarchar(max),
enddate nvarchar(max),
sum nvarchar(max),
currencysum nvarchar(max),
currencyname nvarchar(max),
currencycode nvarchar(max),
code nvarchar(max),
numagreem nvarchar(max),
dateagreem nvarchar(max),
numbernpa nvarchar(max),
datereg nvarchar(max),
namenpa nvarchar(max),
rate nvarchar(max),
dateupdate nvarchar(max),
summba nvarchar(max),
summbamo nvarchar(max),
regnumrgz nvarchar(max),
mfcode nvarchar(max),
mfname nvarchar(max),
npakind nvarchar(max),
sumsubfzfb nvarchar(max),
outersystem nvarchar(max),
internaldocnum nvarchar(max),
loaddate nvarchar(max)
);

create table Grbs (
id_info nvarchar(10) not null primary key,
okopf nvarchar(max),
fullName nvarchar(max),
shortName nvarchar(max),
inn nvarchar(max),
kpp nvarchar(max),
location nvarchar(max),
dateAccount nvarchar(max),
kbkInput nvarchar(max),
grbsAccount nvarchar(max),
codeReestr nvarchar(max),
countryCode nvarchar(max),
countryName nvarchar(max),
regionCode nvarchar(max),
regionName nvarchar(max),
districtName nvarchar(max),
settleName nvarchar(max),
postIndex nvarchar(max),
locationOktmo nvarchar(max),
localCode nvarchar(max),
localName nvarchar(max),
structType nvarchar(max),
streetType nvarchar(max),
objectType nvarchar(max),
buildingType nvarchar(max),
roomType nvarchar(max),
tofkcode nvarchar(max),
tofkname nvarchar(max)
);

create table Documents(
id_info nvarchar(10) not null foreign key references info(id),
id nvarchar(10) not null primary key,
name  nvarchar(max),
date  nvarchar(max),
contentType  nvarchar(max),
)

create table Changes(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
num nvarchar(max),
text  nvarchar(max),
docDate  nvarchar(max),
date  nvarchar(max)
)

create table Payments(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
num nvarchar(max),
date  nvarchar(max),
sumcurrency nvarchar(max),
sumrub nvarchar(max),
rate nvarchar(max),
currencyname nvarchar(max),
currencycode nvarchar(max)
);

create table Marks(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
name nvarchar(max),
value nvarchar(max),
date nvarchar(max),
okei nvarchar(max),
indnum nvarchar(max)
)

create table NPA(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
npaKind nvarchar(max),
npaNumber nvarchar(max),
npaName nvarchar(max),
acceptDate nvarchar(max),
endDate nvarchar(max),
regDate nvarchar(max),
regNum nvarchar(max)
)

create table BO(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
dateAccount nvarchar(max),
dateUnderwrite nvarchar(max),
fio nvarchar(max),
head nvarchar(max),
number nvarchar(max)
);

create table Construct(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
cost nvarchar(max),
power nvarchar(max),
address nvarchar(max),
name nvarchar(max)
);

create table SubjectNpa(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
dateAccept nvarchar(max),
kind nvarchar(max),
name nvarchar(max),
number nvarchar(max)
);

create table InfoSub(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
dateinfo nvarchar(max),
volretsub nvarchar(max),
volrecsub nvarchar(max),
loaddate nvarchar(max)
);

create table InfoCost(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
dateinfo nvarchar(max),
volimplemcost nvarchar(max),
currencycode nvarchar(max),
loaddate nvarchar(max)
);

create table InfoInd(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
dateinfo nvarchar(max),
deviationreason nvarchar(max),
value nvarchar(max),
okei nvarchar(max),
indnum nvarchar(max),
name nvarchar(max),
loaddate nvarchar(max)
);

create table IndicatorValue(
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
value nvarchar(max),
reportdate nvarchar(max),
okei nvarchar(max),
name nvarchar(max),
indnum nvarchar(max),
loaddate nvarchar(max)
);


create table ForeignAddress (
id int not null primary key identity,
countryCode nvarchar(max),
countryName nvarchar(max),
regionCode nvarchar(max),
regionName nvarchar(max),
postIndex nvarchar(max),
localCode nvarchar(max),
localName nvarchar(max),
oktmo nvarchar(max),
struct nvarchar(max),
street nvarchar(max),
streetType nvarchar(max),
[object] nvarchar(max),
districtName nvarchar(max),
settleName nvarchar(max),
buildingType nvarchar(max),
roomType nvarchar(max)
)

create table LocalAddress (
id int not null primary key identity,
countryCode nvarchar(max),
countryName nvarchar(max),
regionCode nvarchar(max),
regionName nvarchar(max),
postIndex nvarchar(max),
localCode nvarchar(max),
localName nvarchar(max),
oktmo nvarchar(max),
struct nvarchar(max),
street nvarchar(max),
streetType nvarchar(max),
[object] nvarchar(max),
districtName nvarchar(max),
settleName nvarchar(max),
buildingType nvarchar(max),
roomType nvarchar(max)
)


create table Receiver (
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
budgetName nvarchar(max),
oktmo nvarchar(max),
fullName nvarchar(max),
shortName nvarchar(max),
fullNameLat nvarchar(max),
okopf nvarchar(max),
inn nvarchar(max),
kpp nvarchar(max),
dateAccount nvarchar(max),
codeRecipient nvarchar(max),
shortNameLat nvarchar(max),
codeReestr nvarchar(max),
accountNum nvarchar(max),
accountOrgCode nvarchar(max),
regCountryCode nvarchar(max),
regCountryName nvarchar(max),
admelement nvarchar(max),
phoneNumber nvarchar(max),
email nvarchar(max),
codeReestrGrbs nvarchar(max),
grbsFullName nvarchar(max),
localAddress int foreign key references LocalAddress(id),
foreinAddress int foreign key references ForeignAddress(id)
);

create table AddAgreement (
id int not null primary key,
id_info nvarchar(10) not null foreign key references info(id),
numadditagreem nvarchar(50),
dateagreem nvarchar(max),
entrydate nvarchar(max),
sumsubcur nvarchar(max),
sumsubrub nvarchar(max)
);

create table PlanTransSub (
id int not null primary key identity,
id_addagreement int not null foreign key references addagreement(id),
kbk  nvarchar(max),
sumcur  nvarchar(max),
rate  nvarchar(max),
currencycode  nvarchar(max),
[period]  nvarchar(max)
)

create table Plans (
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
purpose nvarchar(max),
kbk nvarchar(max),
sumtotal nvarchar(max),
summonty nvarchar(max),
code nvarchar(max),
sumyear nvarchar(max),
sumftyear nvarchar(max),
sumskyear nvarchar(max),
sumtryear nvarchar(max),
sumfryear nvarchar(max),
sumotheryear nvarchar(max),
date nvarchar(max),
analyticalcode nvarchar(max),
sumlastyrexec nvarchar(max),
sumlastyrnexec nvarchar(max),
note nvarchar(max)
);

create table Faip (
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),
sumtotal nvarchar(max),
summonth nvarchar(max),
code nvarchar(max),sumyear nvarchar(max),sumftyear nvarchar(max),sumskyear nvarchar(max),sumtryear nvarchar(max),sumfryear nvarchar(max),sumotheryear nvarchar(max),date nvarchar(max),name nvarchar(max)
);

create table FaipSubject (
id int not null primary key identity,
id_info nvarchar(10) not null foreign key references info(id),name nvarchar(max),
code nvarchar(max),sumyear nvarchar(max),sumftyear nvarchar(max),sumskyear nvarchar(max),sumtryear nvarchar(max),sumfryear nvarchar(max),sumotheryear nvarchar(max),date nvarchar(max),
summonth nvarchar(max)
);

create table PlansSubject (
id int not null primary key,
id_info nvarchar(10) not null foreign key references info(id),
sumLastYrNexec nvarchar(max),
note nvarchar(max),
sumLastYrExec nvarchar(max),
conditsign nvarchar(max),
analyticalCode nvarchar(max),
kbk nvarchar(max),
rate nvarchar(max),
purpose nvarchar(max),
outersystem nvarchar(max),
)

create table Facts (
id int not null primary key identity,
id_planssubject int not null foreign key references PlansSubject(id),
[period]  nvarchar(max),
sumsubcur  nvarchar(max),
sumsubrub  nvarchar(max),
outersystem  nvarchar(max)
);
