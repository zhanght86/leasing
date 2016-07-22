

function Select_car(){
	var speed = 800;
	$(function() {
		getFirstData();
		// 显示车系
		$('#brand_page').on('click', '[_brand]', function() {
			// 取值啦：
			$('[name=brand_id]').val($(this).attr('_brand'));
			getSeriesJson(1, $('#series_page [name=rows]').val(), false);

		});
		//  显示车型
		$('#series_page').on('click', '[_series]', function() {
			// 取值啦：
			$('[name=brand_id]').val($(this).attr('_brand'));
			$('[name=car_series_id]').val($(this).attr('_series'));
			//
			getCarJson(1, $('#car_page [name=rows]').val(), false);

		});
		// 选择车型    隐藏车型车系
		$('#car_page').on( 'click', '[_car_model]', function() {
			// 取值啦：
			$('[name=car_series_id]').val($(this).attr('_car_series'));
			$('[name=car_model_id]').val($(this).attr('_car_model'));
			$('[name=COMPANY_ID]', '#save_FM').val( $(this).attr('_company_id'));
			$('[name=COMPANY_NAME]', '#save_FM').val( $(this).attr('_company_name'));
			$('[name=car_price]', '#save_FM').val( $(this).attr('_car_price'));
			$('[name=car_price_zd]', '#save_FM').val( $(this).attr('_car_price')); // 指导价，只页面用来计算购置税
			$('.car_price', '#save_FM').text($(this).attr('_car_price'));
			
			$("#main_div").show();
			$("#car_page").hide();
			$("#brand_page").hide();
			$("#series_page").hide();
			getFirstData();
		});
		// 品牌反回按钮
		$(document.body).on('click', '.brand_back', function() {
			$("#main_div").show();
			$("#brand_page").hide();
			$("#car_page").hide();
			$("#series_page").hide();
		});
		// 车系反回按钮
		$(document.body).on('click', '.series_back', function() {
			$("#main_div").hide();
			$("#car_page").hide();
			$("#brand_page").show();
			$("#series_page").hide();
		});
		
		// 车型反回按钮
		$(document.body).on('click', '.car_back', function() {
			$("#main_div").hide();
			$("#brand_page").hide();
			$("#car_page").hide();
			$("#series_page").show();
		});

		// 事件源 显示 车品牌
		$(document.body).on('click','#car_brand_name_FORM', function() {
			getBrandsJson(1, $('#brand_page [name=rows]').val(), false);
		});

		//----------------------------------------end ---------------------------------------------
	});
}