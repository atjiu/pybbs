<button id="toggleBigImageBtn" data-toggle="modal" class="hidden" data-target="#showBigImageModal"></button>
<div class="modal fade" tabindex="-1" role="dialog" id="showBigImageModal">
  <div class="modal-dialog" style="width: 98%" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <img src="" id="bigImage" style="max-width: 100%;" alt="">
      </div>
    </div>
  </div>
</div>
<script>
  $(function () {
    $(".show_big_image img").click(function () {
      var src = $(this).attr("src");
      $("#bigImage").attr("src", src);
      $("#toggleBigImageBtn").click();
    });
  })
</script>