//     tSF Briefing
// Do not modify this part
#define BRIEFING		_briefing = [];
#define TOPIC(NAME) 	_briefing pushBack ["Diary", [ NAME,
#define END			]];
#define ADD_TOPICS	for "_i" from (count _briefing) to 0 step -1 do {player createDiaryRecord (_briefing select _i);};
#define TAGS(X)	tSF_MissionTags = X ;
//
//

TAGS(["SPECOPS","INFANTRY"])

// Briefing goes here

BRIEFING

TOPIC("I. Обстановка:")
"     Кризис беженцев в Федеративной Республике Абрамия достиг своего критического пика. Вместе с мирным населением в страну проникли бесчисленные боевики и рекрутеры ISIS и началась гражданская война.
<br />     'Мирные' исламисты поставили для себя задачу полного истребления коренного арийского (и белого впринципе) населения. Исламисты крушат магазины, врываются в дома жителей ФРА и занимаются прочими 'мирными' вещами вроде отрезания голов.
<br />     Канцлер был убит и на данный момент власть и порядок в стране удерживают военные. В связи с этим мы испытываем резкий дефицит солдат для ведения освободительных операций, ибо большинство было выделено на поддержание порядка и патрулирования крупных городов. Потерян контроль над значительной частью страны.
<br />     Недавно стало известно что исламисты захватили военную базу Xray, на которой находилась тяжелая техника (несколько танков) и они готовятся использовать ее для уничтожения близлежащего города. 
<br />     Ситуация критическая, мы не можем позволить террористам воспользоваться техникой, у нас нет ни времени, ни сил чтобы вернуть ее или восстанавливать контроль над областью, поэтому несмотря на ущерб который мы понесем от ее потери, мы должны ее уничтожить. 
<br />     Обстоятельства осложнены тем, что захваченные танки находятся под куполом зенитной самоходной установки M163, находящейся базе Xray, поэтому авиа удар невозможен, а их точное местоположение неизвестно.
<br />     Для решения проблемы был привлечен взвод элитного арийского спецназа KSK, который будет заброшен над потерянной территорией, вне эффективного радиуса зенитного купола, с целью уничтожения M163 и наведения союзной авиации на захваченные танки, после чего силы KSK будут эвакуированы на вертолете.
<br />
<br /> "
END

TOPIC("А. Враждебные силы:")
"Исламские террористы - Нерегулярные войска
<br />     Численность - 1 взвод, с поддержкой M163
<br />"
END

TOPIC("Б. Дружественные силы:")
"Взвод спецназа KSK - Элитные Арийские Воины Германии
<br />     1'1 - 9 чел.
<br />     1'2 - 9 чел.
<br />     1'6 - 4 чел."
END

TOPIC("II. Задание:")
"1) Произвести парашютную высадку над потерянной территорией
<br />2) Перегруппироваться
<br />3) Уничтожить M163 находящуюся на базе Xray
<br />4) Занять самую высокую точку наблюдения в данном районе для нахождения танков и наведения на них авиаудара (The Monastery)
<br />5) Проследовать к точке эвакуации
<br />
<br />ВСЕ Читайте Поддержка"
END

TOPIC("III. Выполнение:")
"По плану командира, "
END

TOPIC("IV. Поддержка:")
"3 C-130
<br />Парашюты
<br />
<br />Короткий гайд как пользоваться парашютом, который может оказаться полезным всем тем кто не планирует размазаться по земле в самом начале миссии.
<br />
<br />1) Оденьте рюкзаки на грудь
<br />Self-Interaction (CTRL + WIN) -> Equipment -> BackPack on Chest
<br />
<br />2) Парашюты в ящике рядом с трапом
<br />Перед тем как прыгать, не забудьте одеть парашют
<br />
<br />3) Для того чтобы следить за высотой на которой вы находитесь, 
<br />Нажмите "" O "" или что у Вас назначено на Часы
<br />
<br />4) Рекомендуемая высота открытия парашюта 300 метров
<br />Для открытия парашюта используйте колесико мыши
<br />
<br />5) Постарайтесь приземлиться на ровную поверхность
<br />чтобы не переломать себе все к чертям
<br />
<br />6) После приземления, сбросьте с себя парашют, и оденьте обратно на спину рюкзак
<br />Self-Interaction (CTRL + WIN) -> Equipment -> BackPack on Back
<br />
<br />...
<br />
<br />8) PROFIT
<br />Теперь вы у мамы ВДВшник"
END

TOPIC("V. Сигналы:")
"PL NET - LR CH 1
<br />     1'1 - SR CH 1
<br />     1'2 - SR CH 2
<br />     1'6 - SR CH 6"
END

TOPIC("VI. Замечания:")
"Achieved by Magnificent 10Dozen's Magic!"
END

ADD_TOPICS