//Mission variables
	if (isNil "ts_tasks") then { ts_tasks = 0; ts_h_dead = 0; ts_h = 0; };

//10Dozen magic
	[] execVM "dzn_tSFramework\dzn_tSFramework_Init.sqf";
	[false] execVM "dzn_gear\dzn_gear_init.sqf";
	[] execVM "dzn_dynai\dzn_dynai_init.sqf";
	[] execVM "dzn_brv\dzn_brv_init.sqf";
	ts_zone = [ts_zone_t, true] call dzn_fnc_convertTriggerToLocation;
	

// Time selection
	if(isServer) then
		{
			_gameTime = 15;
			if(!isNil "paramsArray") then
				{ 
					_gameTime = paramsArray select 0;
				};

			setDate[2016, 5, 20, _gameTime, 00];
		};
		
		
// Move hostages
	0 = [] spawn {
	
		//add delay
			waitUntil {time > 2}; 	
			
		// get pow_2
			pow2 setpos [(getpos pow1 select 0)-1,(getpos pow1 select 1),(4.6)];
			
		// get pow_3			
			pow3 setpos [(getpos pow1 select 0)+1,(getpos pow1 select 1),(4.6)];
	};
