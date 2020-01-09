﻿//     tSF Briefing
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
"72 часа назад наш источник в штабе ВС РФ передал нам информацию о готовящейся на архипелаге Шпицберген операции. Русские планируют в тайне забросить на остров Абрамия группу морской пехоты с неизвестной для нас целью. После анализа полученной информации стало понятно что в зоне действия русских находится станция слежения и перехвата Fisher. Ввиду большой ценности информации а так же оборудования, находящегося на этой станции, командование срочно подготовило план операции по извлечению информационных носителей и скрытию следов деятельности в данном районе."
END

TOPIC("А. Враждебные силы:")
"До взвода Морской пехоты ВМФ РФ, Северный флот. Хорошо подготовленные бойцы, специализирующиеся на ведении боевых действий в условиях севера. Обучены навыкам выживания в условиях низких температур, имеют альпинистскую подготовку."
END

TOPIC("Б. Дружественные силы:")
"Два отряда NORSOF. В распоряжении стандартное вооружение блока НАТО.
<br />     NORSOF 1'1 - 9 чел.
<br />     NORSOF 1'2 - 9 чел.
<br />     NORSOF 1'6 - 4 чел."
END

TOPIC("II. Задание:")
"- Произвести высадку
<br />- Выйти в район станции Fisher 
<br />- Захватить носители информации и декодер
<br />- Уничтожить радио оборудование (радар)
<br />- Проследовать в зону эвакуации"
END

TOPIC("III. Выполнение:")
"Скорее всего не удастся избежать прямого столкновения, поэтому необходимо действовать быстро и решительно. Важно не дать русским опомнится и оказать скоординированное сопротивление. "
END

TOPIC("IV. Поддержка:")
"Отсутствует"
END

TOPIC("V. Сигналы:")
"PL NET - LR CH 1
<br />     NORSOF 1'1 SR CH 1
<br />     NORSOF 1'2 SR CH 2
<br />     NORSOF 1'6 SR CH 6"
END

TOPIC("VI. Замечания:")
"powered by 10Dozen Magic"
END

ADD_TOPICS