create table info (
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

create table grbs (
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

create table documents(
    id_info nvarchar(10) not null foreign key references info(id),
    id nvarchar(10) not null primary key,
    name  nvarchar(max),
    date  nvarchar(max),
    contentType  nvarchar(max),
)

create table changes(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    num nvarchar(max),
    text  nvarchar(max),
    docDate  nvarchar(max),
    date  nvarchar(max)
)

create table payments(
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

create table marks(
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

create table construct(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    cost nvarchar(max),
    power nvarchar(max),
    address nvarchar(max),
    name nvarchar(max)
);

create table subjectnpa(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    dateAccept nvarchar(max),
    kind nvarchar(max),
    name nvarchar(max),
    number nvarchar(max)
);

create table infosub(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    dateinfo nvarchar(max),
    volretsub nvarchar(max),
    volrecsub nvarchar(max),
    loaddate nvarchar(max)
);

create table infocost(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    dateinfo nvarchar(max),
    volimplemcost nvarchar(max),
    currencycode nvarchar(max),
    loaddate nvarchar(max)
);

create table infoind(
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

create table indicatorvalue(
    id int not null primary key identity,
    id_info nvarchar(10) not null foreign key references info(id),
    value nvarchar(max),
    reportdate nvarchar(max),
    okei nvarchar(max),
    name nvarchar(max),
    indnum nvarchar(max),
    loaddate nvarchar(max)
);
