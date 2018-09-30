var app = angular.module('app', ['ui.grid','ui.grid.pagination']);

app.controller('UserCtrl', ['$scope','UserService', 
                               function ($scope, UserService) {
                                   var paginationOptions = {
                                       pageNumber: 1,
                                       pageSize: 5,
                                   sort: null
                                   };
                            
                               UserService.getUsers(
                                 paginationOptions.pageNumber,
                                 paginationOptions.pageSize).success(function(data){
                                   $scope.gridOptions.data = data.content;
                                   $scope.gridOptions.totalItems = data.totalElements;
                                 });
                            
                               $scope.gridOptions = {
                                   paginationPageSizes: [5, 10, 20],
                                   paginationPageSize: paginationOptions.pageSize,
                                   enableColumnMenus:false,
                               useExternalPagination: true,
                                   columnDefs: [
                                      { name: 'id' },
                                      { name: 'firstName' },
                                      { name: 'lastName' },
                                      { name: 'email' },
                                      { name: 'birthDate'}
                                   ],
                                   onRegisterApi: function(gridApi) {
                                      $scope.gridApi = gridApi;
                                      gridApi.pagination.on.paginationChanged(
                                        $scope, 
                                        function (newPage, pageSize) {
                                          paginationOptions.pageNumber = newPage;
                                          paginationOptions.pageSize = pageSize;
                                          UserService.getUsers(newPage,pageSize)
                                            .success(function(data){
                                              $scope.gridOptions.data = data.content;
                                              $scope.gridOptions.totalItems = data.totalElements;
                                            });
                                       });
                                   }
                               };
                           }]);

app.service('UserService',['$http', function ($http) {
	 
    function getUsers(pageNumber,size) {
        pageNumber = pageNumber > 0?pageNumber - 1:0;
        return $http({
          method: 'GET',
            url: 'user?page='+pageNumber+'&size='+size
        });
    }
    return {
        getUsers: getUsers
    };
}]);