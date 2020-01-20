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
"Провинция Кундуз остается неспокойным местом в Афганистане. Вчера мы получили информацию о возможной атаке на  аванпост 'Cheetah', поэтому с сегодняшнего дня мы усиляем патрульную деятельность в районе."
END

TOPIC("А. Враждебные силы:")
"От 2 отделений до взвода инсургентов."
END

TOPIC("Б. Дружественные силы:")
"На юге расположен аванпост 'Cheetah' - при необходимости отступайте туда."
END

TOPIC("II. Задание:")
"В составе 1 отделения (Браво-3-1) проведите осмотр местности и проверьте кампаунды в районе контрольных точек 1, 2, 3 и 4. <br /><br />Будьте готовы к вооруженному сопротивлению."
END

TOPIC("III. Выполнение:")
"Согласно плану командира отделения.<br /><br />"
END

TOPIC("IV. Поддержка:")
"- 1x MRAP RG33<br />- 1x HMMWV M1025A1 Mk.19<br />- 1x отделение (Браво-3-2) (резерв)<br />- 1x M113 (Дельта-4-1) (резерв)"
END

TOPIC("V. Сигналы:")
"PL NET - LR CH 1<br />  BRAVO 3'1 - SR CH 1<br />  BRAVO 3'2 - SR CH 2"
END

TOPIC("VI. Замечания:")
"Все решения остаются на совести непосредственных участников событий."
END

ADD_TOPICS