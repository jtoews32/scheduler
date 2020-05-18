<!DOCTYPE html>
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<link href='packages/core/main.css' rel='stylesheet' />
<link href='packages/daygrid/main.css' rel='stylesheet' />
<link href='packages/timegrid/main.css' rel='stylesheet' />
<script src='packages/core/main.js'></script>
<script src='packages/interaction/main.js'></script>
<script src='packages/daygrid/main.js'></script>
<script src='packages/timegrid/main.js'></script>
<script src='packages-premium/resource-common/main.js'></script>
<script src='packages-premium/resource-daygrid/main.js'></script>
<script src='packages-premium/resource-timegrid/main.js'></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="
sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

<script type="text/javascript">
  $(document).ready(function() {
    $("#bookAppointmentModalButton").hide();
    $("#removeAppointmentModalButton").hide();

    var getStartTime = function(date) {
      return new Date(date.toString().split('GMT')[0]+' UTC').toISOString().replace(".000Z", "");
    }

    var prettyTimeText = function (date) {
      let options = {
        weekday: 'long',
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      };
      return "15 minute or 1 hour appoints slots offered, " +  date.toLocaleString('en-us', options) ; 
    }

    var showAppointments = function ( calendar ) {
      axios.get('http://localhost:8080/events')
      .then(function (response) {
        response.data.forEach( function ( event ) { 
          calendar.addEvent({
            id: event.id,
            resourceId: 'resource', 
            start: event.start.replace(".000+0000", ""),
            end: event.end.replace(".000+0000", ""),
            title: event.title,
          });
        });
        calendar.render(); 
      });
    }

    var bookAppointment = function(calendar, startTime, name, duration) {
      axios.post('http://localhost:8080/events', {
        resourceId: 'resource', 
        start: startTime, 
        end: "0000-00-00T00:00:00.000+0000", 
        title: name, 
        duration: duration
      })
      .then(function (response) { 
        calendar.addEvent({
          id: response.data.id,
          resourceId: 'resource', 
          start: response.data.start.replace(".000+0000", ""),
          end: response.data.end.replace(".000+0000", ""),
          title: response.data.title,
        });
        calendar.render(); 
      }); 
    }

    var unbookAppointment = function(calendar, id) {
      axios.delete('http://localhost:8080/events/' + id)
      .then(function (response) {
        var event = calendar.getEventById(id);
        event.remove();
        calendar.render(); 
      });
    }

    var calendarEl = document.getElementById('calendar');
    const calGMT = Date().toString().split('GMT')[0]+' UTC';
    const calDefault = (new Date(calGMT).toISOString().split('T')[0]);

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'resourceDayGrid', 'resourceTimeGrid' ],
      defaultView: 'resourceTimeGridDay',
      defaultDate: calDefault,
      slotDuration: '00:15:00',
      snapDuration: '00:15:00',
      allDaySlot: false,
      minTime: '08:00:00',
      maxTime: '17:15:00',
      weekends: false,
      schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
      eventColor: '#E0ECF8',
      selectable: true,
      eventLimit: true, 
      eventOverlab: false,
      editable: false,
      droppable: false, 
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'resourceTimeGridDay,timeGridWeek,dayGridMonth'  
      },
      views: {},
      resources: [{id: 'resource', title: ' '},],
      events: [],

      eventClick: function(info) {
        $("#removeTextBody").text( "\"" + info.event.title + "\" will be removed from the calendar");
        $("#removeAppointmentModalButton").data("id", info.event.id); 
        $("#removeAppointmentModalButton").click();
      },

      dateClick: function(arg) {
        if(arg.view.type == 'dayGridMonth') {
          return;
        }
  
        $("#eventNameInput").val('');
        $("#scheduleTextBody").text(prettyTimeText(arg["date"]));
        $("#eventNameInput" ).removeClass("alert alert-danger");
        $("#bookAppointmentModalButton").data("start", getStartTime(arg["date"])); 
        $("#bookAppointmentModalButton").click();
      }
    });

    showAppointments(calendar);
    calendar.render();

    $("#book15Button").on("click", function(){
      if( $("#eventNameInput").val().length === 0) {
        $("#eventNameInput").addClass("alert alert-danger");
        return;
      }
      bookAppointment(calendar, $("#bookAppointmentModalButton" ).data("start") , $("#eventNameInput").val(), 15);
      $("#closeScheduleModalButton").click();
    });

    $("#book60Button").on("click", function(){
      if($("#eventNameInput").val().length === 0) {
        $("#eventNameInput").addClass( "alert alert-danger" );
        return;
      }

      bookAppointment(calendar,$("#bookAppointmentModalButton").data("start") , $("#eventNameInput").val(), 60);
      $("#closeScheduleModalButton").click();
    });

    $("#removeAppointmentButton").on("click", function(){
      unbookAppointment( calendar,$( "#removeAppointmentModalButton").data("id"));
      $("#closeDeleteModalButton").click();
    });
  });
</script>
<style>
  body {
    margin: 0;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }
  #calendar {
    max-width: 900px;
    margin: 50px auto;
  }
</style>
</head>
<body class="bg-transparent">
  <div id='calendar'></div>
  <div id="scheduleModal"  class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Book appointment slot</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" >
          <p id="scheduleTextBody">15 minute and 1 hour appointments are offered.</p>
          <form>
            <div class="form-group">
              <label for="eventNameInput">Name the event</label>
              <input id="eventNameInput" class="form-control form-control-sm " type="text" placeholder="">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button id="book15Button" type="button" class="btn btn-primary">Book 15 minutes</button>
          <button id="book60Button" type="button" class="btn btn-primary">Book 1 hour</button>
          <button id="closeScheduleModalButton" type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalLabel">Modal title</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div id="removeTextBody" class="modal-body">
          Delete this appointment slot from the calendar 
        </div>
        <div class="modal-footer">
          <button id="removeAppointmentButton" type="button" class="btn btn-primary">Yes</button>
          <button id="closeDeleteModalButton" type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
        </div>
      </div>
    </div>
  </div>
  <button id="removeAppointmentModalButton" class="display: none; visibility: hidden; btn btn-primary" type="button" data-toggle="modal" data-target="#deleteModal">button</button>
  <button id="bookAppointmentModalButton" class="display: none; visibility: hidden; btn btn-primary" type="button" data-toggle="modal" data-target="#scheduleModal" data-whatever="@mdo">button</button>
</body>
</html>