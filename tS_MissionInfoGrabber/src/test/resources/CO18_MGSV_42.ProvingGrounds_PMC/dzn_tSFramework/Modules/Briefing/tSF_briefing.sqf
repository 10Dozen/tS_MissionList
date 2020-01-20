//     tSF Briefing
// Do not modify this part
#define BRIEFING		_briefing = [];
#define TOPIC(NAME) 	_briefing pushBack ["Diary", [ NAME,
#define END			]];
#define ADD_TOPICS	for "_i" from (count _briefing) to 0 step -1 do {player createDiaryRecord (_briefing select _i);};
//
//
// Briefing goes here

BRIEFING

TOPIC("I. Обстановка:")
"Мы получили новый контракт в Южной Африке. Трое американских горных инженера попали в плен к африканским повстанцам. Есть предположение, что их держат в заброшенном карьере, информации о выкупе пока что не поступало, нас настоятельно просят разрешить эту ситуацию в кротчайшие сроки, пока это не стало достоянием прессы.
<br />Карьер был закрыт около 20 лет назад, и за это время местные банды возвели там свои трущобы, нам были переданы фотографии с самолета, по которым мы смогли обновить план местности."
END

TOPIC("А. Враждебные силы:")
"Нерегулярные силы противника,
<br />Африканские повстанцы, 1 взвод"
END

TOPIC("Б. Дружественные силы:")
"Элитные силы Outer Heaven,
<br />Взвод наемников
<br />1'1 - 9 чел.
<br />1'2 - 9 чел.
<br />1'6 - 4 чел."
END

TOPIC("II. Задание:")
"Освободить захваченных  BRIEFIng  горных инженеров и сопроводить их к ""Insertion Point"""
END

TOPIC("III. Выполнение:")
"По плану командира"
END

TOPIC("IV. Поддержка:")
"5 автомобилей UAZ, невооруженных"
END

TOPIC("V. Сигналы:")
"PL NET - LR 1
<br />      1'1- SR CH 1
<br />      1'2- SR CH 2
<br />      1'6- SR CH 6"
END

TOPIC("VI. Замечания:")
"Achieved by 10Dozen Magic"
END

ADD_TOPICS