
$(function() {
  $(".treemenu .lv1btn,.treemenu .lv1body .lv2btn,.treemenu .lv1body .lv2body .lv3btn").click(function() {
    $(this).next().slideToggle(100);
  });
});