app.controller('TableCtrl', ['$scope', '$timeout','$filter', '$http','editableOptions', 'editableThemes', function($scope, $timeout,$filter, $http, editableOptions, editableThemes) {
	
	$http.get(WX.constants.path + "userController/showUsers").success(function(data){
		console.info('data',data);
	});
	
	
	$scope.rowCollectionBasic = [
      {firstName: 'Laurent', lastName: 'Renard', birthDate: new Date('1987-05-21'), balance: 102, email: 'whatever@gmail.com'},
      {firstName: 'Blandine', lastName: 'Faivre', birthDate: new Date('1987-04-25'), balance: -2323.22, email: 'oufblandou@gmail.com'},
      {firstName: 'Francoise', lastName: 'Frere', birthDate: new Date('1955-08-27'), balance: 42343, email: 'raymondef@gmail.com'}
  ];

  $scope.removeRow = function(row) {
      var index = $scope.rowCollectionBasic.indexOf(row);
      if (index !== -1) {
          $scope.rowCollectionBasic.splice(index, 1);
      }
  };

  $scope.predicates = ['firstName', 'lastName', 'birthDate', 'balance', 'email'];
  $scope.selectedPredicate = $scope.predicates[0];

  var firstnames = ['Laurent', 'Blandine', 'Olivier', 'Max'];
  var lastnames = ['Renard', 'Faivre', 'Frere', 'Eponge'];
  var dates = ['1987-05-21', '1987-04-25', '1955-08-27', '1966-06-06'];
  var id = 1;

  function generateRandomItem(id) {

      var firstname = firstnames[Math.floor(Math.random() * 3)];
      var lastname = lastnames[Math.floor(Math.random() * 3)];
      var birthdate = dates[Math.floor(Math.random() * 3)];
      var balance = Math.floor(Math.random() * 2000);

      return {
          id: id,
          firstName: firstname,
          lastName: lastname,
          birthDate: new Date(birthdate),
          balance: balance
      }
  }

  $scope.rowCollection = [];

  for (id; id < 5; id++) {
      $scope.rowCollection.push(generateRandomItem(id));
  }

  //copy the references (you could clone ie angular.copy but then have to go through a dirty checking for the matches)
  $scope.displayedCollection = [].concat($scope.rowCollection);

  //add to the real data holder
  $scope.addRandomItem = function addRandomItem() {
      $scope.rowCollection.push(generateRandomItem(id));
      id++;
  };

  //remove to the real data holder
  $scope.removeItem = function(row) {
      var index = $scope.rowCollection.indexOf(row);
      if (index !== -1) {
          $scope.rowCollection.splice(index, 1);
      }
  }

  //  pagination
  $scope.itemsByPage=10;

  $scope.rowCollectionPage = [];
  for (var j = 0; j < 200; j++) {
    $scope.rowCollectionPage.push(generateRandomItem(j));
  }

  // pip
  var promise = null;
  $scope.isLoading = false;
  $scope.rowCollectionPip = [];
  $scope.getPage = function() {
    $scope.rowCollectionPip=[];
    for (var j = 0; j < 20; j++) {
      $scope.rowCollectionPip.push(generateRandomItem(j));
    }
  }

  $scope.callServer = function getData(tableState) {
      //here you could create a query string from tableState
      //fake ajax call
      $scope.isLoading = true;

      $timeout(function () {
          $scope.getPage();
          $scope.isLoading = false;
      }, 2000);
  };

 $scope.getPage();
 
 editableThemes.bs3.inputClass = 'input-sm';
 editableThemes.bs3.buttonsClass = 'btn-sm';
 editableOptions.theme = 'bs3';

 $scope.html5 = {
   email: 'email@example.com',
   tel: '123-45-67',
   number: 29,
   range: 10,
   url: 'http://example.com',
   search: 'blabla',
   color: '#6a4415',
   date: null,
   time: '12:30',
   datetime: null,
   month: null,
   week: null
 };

 $scope.user = {
 	name: 'awesome',
 	desc: 'Awesome user \ndescription!',
   status: 2,
   agenda: 1,
   remember: false
 }; 

 $scope.statuses = [
   {value: 1, text: 'status1'},
   {value: 2, text: 'status2'},
   {value: 3, text: 'status3'}
 ];

 $scope.agenda = [
   {value: 1, text: 'male'},
   {value: 2, text: 'female'}
 ];

 $scope.showStatus = function() {
   var selected = $filter('filter')($scope.statuses, {value: $scope.user.status});
   return ($scope.user.status && selected.length) ? selected[0].text : 'Not set';
 };

 $scope.showAgenda = function() {
   var selected = $filter('filter')($scope.agenda, {value: $scope.user.agenda});
   return ($scope.user.agenda && selected.length) ? selected[0].text : 'Not set';
 };

 // editable table
 $scope.users = [
   {id: 1, name: 'awesome user1', status: 2, group: 4, groupName: 'admin'},
   {id: 2, name: 'awesome user2', status: undefined, group: 3, groupName: 'vip'},
   {id: 3, name: 'awesome user3', status: 2, group: null}
 ];

 $scope.groups = [];
 $scope.loadGroups = function() {
   return $scope.groups.length ? null : $http.get('api/groups').success(function(data) {
     $scope.groups = data;
   });
 };

 $scope.showGroup = function(user) {
   if(user.group && $scope.groups.length) {
     var selected = $filter('filter')($scope.groups, {id: user.group});
     return selected.length ? selected[0].text : 'Not set';
   } else {
     return user.groupName || 'Not set';
   }
 };

 $scope.showStatus = function(user) {
   var selected = [];
   if(user && user.status) {
     selected = $filter('filter')($scope.statuses, {value: user.status});
   }
   return selected.length ? selected[0].text : 'Not set';
 };

 $scope.checkName = function(data, id) {
   if (id === 2 && data !== 'awesome') {
     return "Username 2 should be `awesome`";
   }
 };

 $scope.saveUser = function(data, id) {
   //$scope.user not updated yet
   angular.extend(data, {id: id});
   // return $http.post('api/saveUser', data);
 };

 // remove user
 $scope.removeUser = function(index) {
   $scope.users.splice(index, 1);
 };

 // add user
 $scope.addUser = function() {
   $scope.inserted = {
     id: $scope.users.length+1,
     name: '',
     status: null,
     group: null 
   };
   $scope.users.push($scope.inserted);
 };

}]);