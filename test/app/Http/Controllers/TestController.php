<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller as BaseController;

class TestController extends BaseController
{
    //

    public function test(Request $request){

        //dd($request);

        return json_encode(["name", "lname"]);
    }

    public function getHistory(){

    }
}
