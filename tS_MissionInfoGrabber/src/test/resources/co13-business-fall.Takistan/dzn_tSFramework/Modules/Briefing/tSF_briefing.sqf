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

if (side player == west) then {
	TOPIC("I. Обстановка:")
	"Только что стало известно о крушении частного вертолета в горах около н.п. Муладост. На борту находился MVP в сопровождении охраны, судя по последней радиопередаче - MVP выжил. 
	<br />
	<br />Однако крушение произошло в непосредственной близости от зоны активности инсургентов и MVP требуется немедленная эвакуация."
	END

	TOPIC("А. Враждебные силы:")
	"Инсургенты, до взвода."
	END

	TOPIC("Б. Дружественные силы:")
	"- (Возможно) Охрана MVP (1-4 человека)"
	END

	TOPIC("II. Задание:")
	"- Обнаружить и эвакуировать MVP"
	END

	TOPIC("III. Выполнение:")
	"По плану командира.
	<br />Постарайтесь опередить инсургентов."
	END

	TOPIC("IV. Поддержка:")
	"- 1х M1025A2 (M2)
	<br />- 1х M1025A2 (Mk19)
	<br />- 1x M1025A2 
	<br />- 1x M097A2 (Half) "
	END

	TOPIC("V. Сигналы:")
	"Alpha 1'1 - SR 1"
	END

	TOPIC("VI. Замечания:")
	"Powered by dzn_tSF, dzn_dynai, dzn_gear"
	END
} else {
	TOPIC("I. Обстановка:")
	"Наш вертолет потерпел крушение в горах около н.п. Муладост. MVP выжил, но район наполнен инсургентами.
	<br />
	<br />Скорее всего к нам на помощь уже выдвинулись сила армии США, однако наша жизнь в наших руках."
	END

	TOPIC("А. Враждебные силы:")
	"Инсургенты, до взвода."
	END

	TOPIC("Б. Дружественные силы:")
	"- отделение пехоты США"
	END

	TOPIC("II. Задание:")
	"- Защитить MVP до подхода подкреплений"
	END

	TOPIC("III. Выполнение:")
	"По плану командира."
	END

	TOPIC("IV. Поддержка:")
	"Нет"
	END

	TOPIC("V. Сигналы:")
	"Личная охрана - SR 1"
	END

	TOPIC("VI. Замечания:")
	"Powered by dzn_tSF, dzn_dynai, dzn_gear"
	END
};

ADD_TOPICS