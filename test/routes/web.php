<?php


/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

//$app->get('/test', function () use ($app) {
//    return $app->version();
//});

$app->post('/save-patrol', function (\Illuminate\Http\Request $request)
{
    $data = $request->json()->all();


    if ($request->isJson()) {
        $data = $request->json()->all();
    }
    else {
        $data = $request->all();
    }

    dd($data);
});


$app->post('/complete-patrol', 'PatrolManagerController@addPatrol');
$app->post('/history', 'PatrolManagerController@getHistory');
$app->get('/date-history/{date}', 'PatrolManagerController@getDateHistory');
$app->get('/history-logs/{id}', 'PatrolManagerController@getLogs');

$app->get('/patrol-and-logs/{id}', 'PatrolManagerController@getPatrolWithLogs');