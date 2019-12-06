
var TagsMarkdown = {
	"SPECOPS": { 		bg: "#8ab62f", text: "whitesmoke" },
	"INFANTRY": {		bg: "#ba2b2b", text: "whitesmoke" },
	"COMB.ARMS": {		bg: "#596816", text: "whitesmoke" },
	"JTAC/CAS": { 		bg: "#6aa29e", text: "whitesmoke" },
	"ARMOR": { 			bg: "#986f2a", text: "whitesmoke" },
	"RolePlay":  { 		bg: "#ae33ff", text: "whitesmoke" },
	"default": { 		bg: "#8374aa", text: "whitesmoke" }
};

var GridModelClass = function (data) {
	this.data = [...data];
	this.size = data.length;
	this.sortedBy = { field: "", order: true };

	this.filteredOut = [];
	this.filter = {
		byString: function (v, vTofind) {
			return (v.toLocaleLowerCase()).indexOf(vTofind.toLocaleLowerCase()) >= 0;
		}
		, byNumberGTE: function (v, vToFind) {
			return vToFind <= v;
		}
		, byNumberLTE: function (v, vToFind) {
			return vToFind >= v;
		}

		, terrainValues: []
		, field2filter: {
			title: "byString"
			, terrain: "byString"
			, slotsFrom: "byNumberGTE"
			, slotsTo: "byNumberLTE"
		}
		, field2scheme: {
			title: "title"
			, terrain: "terrain"
			, slotsFrom: "player_count"
			, slotsTo: "player_count"
		}
	}

	this.view = null;

	this.sortBy = function (param) {
		let order = true;
		if (this.sortedBy.field == param) { // Toggles sort order if already sorted
			order = !this.sortedBy.order;
		}

		this.sortColumnWithOrder(param, order);
	};

	this.sortColumnWithOrder = function (param, isAscending) {
		this.data.sort(function (a,b) {
			let v1 = a[param];
			let v2 = b[param];
			let r = 0;
			
			if (v1 > v2) {
				r = 1;
			} else if (v1 < v2) {
				r = -1;
			} else {
				r = 0;
			}

			return isAscending ? r : -1 * r;
		});

		this.sortedBy.field = param;
		this.sortedBy.order = isAscending;

		this.refreshView();
		return;
	};

	this.filterBy = function (filterData) {
		this.resetFilter();
		
		if (filterData.length == 0) { // Exit on filter reset action
			this.refreshView(); 
			return; 
		}

		let filteredIndexes = [];
		for (let i = 0; i < filterData.length; ++i) {
			let filterField = filterData[i][0];
			let filterValue = filterData[i][1];

			let filterType = this.filter.field2filter[filterField];
			let schemeField = this.filter.field2scheme[filterField];

			let filterFunction = this.filter[filterType];
			this.data.forEach(function (el) {
				let result = filterFunction(el[schemeField], filterValue);

				// Add indexes that not correspond to filter value to filteredIndexeses

				if (!result && !filteredIndexes.includes( el["id"] )) {
					filteredIndexes.push( el["id"] );
				}
			});
		}

		this.filteredOut = filteredIndexes;
		this.itrPosition = this.getNextFilteredIteratorPos();

		this.refreshView();
	};
	
	this.resetFilter = function () {
		this.filteredOut = [];
		this.resetIterator();
	};

	this.isFiltered = function () {
		return this.filteredOut.length != 0;
	};

	this.prepareFilterData = function () {
		if (this.filter.terrainValues.length == 0) {
			let arr = [];
			this.data.forEach(function (el) {
				if (!arr.includes(el.terrain)) {
					arr.push(el.terrain)
				}
			});

			arr.sort();
			this.filter.terrainValues = arr;
		};
	};

	/* Update view */
	this.refreshView = function () {
		this.prepareFilterData();
		this.resetIterator();
		this.view.refreshGrid(this);
	};

	this.refreshMissionDetails = function(id) {
		if (id < 0) {
			this.view.modal_hidePopup();
			return;
		}

		let mData = this.data.find(e => e.id == id);
		this.view.modal_showPopup(mData);
	};

	/* Iterator */
	this.itrPosition = 0;

	this.getNextFilteredIteratorPos = function () {
		let nextPos = this.itrPosition + 1;
		if (nextPos >= this.size) { return -1; }

		nextPosId = this.data[nextPos]["id"];

		while ( this.filteredOut.includes( nextPosId ) ) {
			++nextPos;

			if (nextPos >= this.size) { return -1; };
			nextPosId = this.data[nextPos]["id"];
		}

		if (nextPos < this.size) {
			return nextPos;
		} else {
			return -1;
		}
	};

	this.hasNext = function () {
		let isOutOfBounds = (this.itrPosition >= this.size) || (this.itrPosition < 0);
		if (isOutOfBounds) { return false; }
		
		let isFilteredOut = this.filteredOut.includes(this.data[this.itrPosition]["id"]);
		return !isFilteredOut;
	};

	this.next = function () {
		let indx = this.itrPosition;

		if (this.isFiltered()) {
			this.itrPosition = this.getNextFilteredIteratorPos();
		} else {
			this.itrPosition++;
		}

		return this.data[indx];
	};

	this.resetIterator = function () {
		this.itrPosition = -1;
		this.itrPosition = this.getNextFilteredIteratorPos();
		return;
	};
};

var GridViewClass = function () {
	this.$grid = "#grid";
	this.$popup = "#popup";

	this.$filter_head = "#grid-filter tr th";
	this.$filter_terrain = "#grid-filter tr td[filter-type='terrain'] select";
	this.$filter_lines = ".filter-line";

	this.header_columns = ["title","player_count","terrain","overview","tags","briefing"];
	this.controller = null;

	this.refreshGrid = function(model) {
		this.clearGrid();
		this.filter_prepareFilter(model.filter.terrainValues);

		if (model.isFiltered()) {
			$(this.$filter_head).toggleClass("filter-active", true);
		} else {
			$(this.$filter_head).toggleClass("filter-active", false);
			this.filter_resetFilter();
		}

		while (model.hasNext()) {
			let info = model.next();
			let tags = this.tags_prepareTags(info.tags);

			$(this.$grid).append(`<tr class="grid-line" mission-id="${info.id}">`
				+ `<td>${info.title}</td>`
				+ `<td class="td-center">${info.player_count}</td>`
				+ `<td>${info.terrain}</td>`
				+ `<td class="td-overview">${info.overview}</td>`
				+ `<td class="td-tags">${tags}</td>`
				+ `<td class="td-center btn-see-more">⇱ Details</td>`
			+ "</tr>");
		}

		this.header_showSortedIcon(model.sortedBy.field, model.sortedBy.order);
		this.controller.initEvents();
	}

	this.clearGrid = function() {
		this.controller.removeEvents();
		$(this.$grid).find("tr[class='grid-line']").each(function () { $(this).remove(); });
	}

	this.header_showSortedIcon = function (column, isAscending) {
		let columnList = this.header_columns;

		$(`${this.$grid} th`).each(function (indx) {
			$(this).find("div").each(function () { $(this).remove(); });

			if (column == columnList[indx]) {
				$(this).append(isAscending ? "<div order='asc'>▲</div>" : "<div order='desc'>▼</div>");
			}
		});
	}

	this.filter_prepareFilter = function (valuesTerrain) {
		if ($(`${this.$filter_terrain} option`).length > 0) { return; }

		$(this.$filter_terrain).append(`<option></option>`);
		for (let i = 0; i < valuesTerrain.length; ++i) {
			$(this.$filter_terrain).append(`<option>${valuesTerrain[i]}</option>`);
		}

		$(this.$filter_lines).each(function () { $(this).css("display", "none") })
	}

	this.filter_resetFilter = function () {
		$(`.td-filter-inputs input, .td-filter-inputs select`).each(function () { $(this).val("") });
	}

	this.modal_showPopup = function (data) {
		$(`${this.$popup} h1`).text(data.title);
		$(`${this.$popup} p[class='modal-terrain']`).text("at " + data.terrain);
		$(`${this.$popup} p[class='modal-tags']`).html(this.tags_prepareTags(data.tags));
		$(`${this.$popup} img`).attr("src", data.map_shot);
		$(`${this.$popup} p[class='modal-briefing']`).html(data.briefing);
		$(this.$popup).css("display","block");
	}

	this.modal_hidePopup = function () {
		$(this.$popup).css("display","none");
	}

	this.tags_prepareTags = function (tags) {
		let tagHtml = "";

		tags.forEach(function (tag) {
			let tagData = TagsMarkdown[tag];
			if (tagData == null) {
				tagData = TagsMarkdown.default;
			}
			tagHtml = tagHtml.concat(`<span class="tag" style="background-color: ${tagData.bg}; color: ${tagData.text}">${tag}</span>`);
		});

		return tagHtml;
	}
}

var GridControllerClass = function () {
	this.model = null;
	this.headerEventsSet = false;
	this.filtersCollapsed = true;

	this.$grid_sortable = "#grid th[sortable='true']";
	this.$btn_popupClose = "#popup span[class='close'";
	this.$btn_seeMore = "#grid tr td[class*='btn-see-more']";

	this.$filter_head = "#grid-filter tr th";
	this.$filter_doFilter = "#btn-filter";
	this.$filter_resetFitler = "#btn-reset-filter";
	this.$filter_lines = ".filter-line";

	this.$filter_byTitle = "td[filter-type='title'] input";
	this.$filter_byTerrain = "td[filter-type='terrain'] select";
	this.$filter_bySlotsFrom = "td[filter-type='slots-gte'] input";
	this.$filter_bySlotsTo = "td[filter-type='slots-lte'] input";

	this.removeEvents = function () {
		$(this.$btn_seeMore).off();
	};

	this.initEvents = function () {
		this.removeEvents();

		/* Static elements: Grid header, Filter form, Modal window */
		if (!this.headerEventsSet) {
			/* Sortable header */
			$(this.$grid_sortable).on("click", this, function (event) {
				let cname = $(this).attr("column_name");
				let model = event.data.model;
				model.sortBy(cname);
			})

			/* Filters */
			$(this.$filter_head).on("click", this, function (event) {
				let controller = event.data;
				controller.filtersCollapsed = !controller.filtersCollapsed; // Toggle filter collapsed
				$(controller.$filter_lines).each(function () { $(this).css("display",  controller.filtersCollapsed ? "none" : "") })
			});

			$(this.$filter_doFilter).on("click", this, function (event) {
				let controller = event.data;
				let model = event.data.model;

				let byTitle = $(controller.$filter_byTitle).val();
				let byTerrain = $(controller.$filter_byTerrain).val();
				let bySlotsFrom = $(controller.$filter_bySlotsFrom).val();
				let bySlotsTo = $(controller.$filter_bySlotsTo).val();

				// Reset filters if empty filter used
				if (byTitle == "" && byTerrain == "" && bySlotsFrom == "" && bySlotsTo == "") {
					model.filterBy([]);
					return;
				}

				let params = [];
				if (byTitle != "") { params.push(["title", byTitle]) };
				if (byTerrain != "") { params.push(["terrain", byTerrain]) };
				if (bySlotsFrom != "") { params.push(["slotsFrom", parseInt(bySlotsFrom)]) };
				if (bySlotsTo != "") { params.push(["slotsTo", parseInt(bySlotsTo)]) };
				
				model.filterBy(params);
			})

			$(this.$filter_resetFitler).on("click", this, function (event) {
				let model = event.data.model;
				model.filterBy([]);
			})

			/* Modal window */
			$(this.$btn_popupClose).on("click", this, function (event) {
				let model = event.data.model;
				model.refreshMissionDetails(-1);
			})

			this.headerEventsSet = true;
		}

		/* Details button */
		$(this.$btn_seeMore).on("click", this, function (event) {
			let model = event.data.model;
			let missionId = parseInt( $(this).parent().attr("mission-id") );

			model.refreshMissionDetails(missionId);
		})
	};
}

$( document ).ready(function () {
	console.log("KEK Ready");

	GridApp = {};
	GridApp.model = new GridModelClass(missionsInfo);
	GridApp.view = new GridViewClass();
	GridApp.controller = new GridControllerClass();

	GridApp.model.view = GridApp.view;
	GridApp.view.controller = GridApp.controller;
	GridApp.controller.model = GridApp.model;

	/* Init */
	GridApp.model.refreshView();
})