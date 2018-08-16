<!doctype html>
<html>

<head>
  <title>Test Application</title>
  <!-- styles -->
  <link rel="stylesheet" href="libs/bootstrap-css/bootstrap.min.css" />
  <link rel="stylesheet" href="app/styles/style.css" />
  
  <!-- scripts -->
  <script src="libs/jquery/jquery.js"></script>
  <script src="libs/bootstrap-css/bootstrap.min.js"></script>
  <script src="libs/angular/angular.js"></script>
  <script src="libs/angular-route/angular-route.js"></script>

  <script src="app/app.js"></script>
  <script src="app/services.js"></script>
</head>

<body ng-app="testApp">
    <div ng-view></div>
</body>

</html>