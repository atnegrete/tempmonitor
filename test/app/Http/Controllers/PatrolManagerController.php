<?php

namespace App\Http\Controllers;

use App\Log;
use DB;
use App\Patrol;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Laravel\Lumen\Routing\Controller as BaseController;

class PatrolManagerController extends BaseController
{

    public function getDateHistory($date){

        $patrols = Patrol::whereDate('created_at', '=', $date.' 00:00:00')->get();

        return $patrols;
    }

    public function getHistory(){
        $data = Patrol::all();

        return $data;
    }

    public function getLogs($id) {

        if($id == '-1'){

            return Log::all();

        }else{

            $patrol = Patrol::find($id);
            $logs = $patrol->logs()->get();

            return $logs;
        }
    }

    public function getPatrolWithLogs($id) {

        if($id == '-1'){

            $patrols = Patrol::all();
            foreach($patrols as $patrol){
                $logs = $patrol->logs();
                $patrol->logs = $logs;
            }

            return $patrols;

        }else{

            $patrol = Patrol::find($id);
            $logs = $patrol->logs();
            $patrol->logs = $logs;

            return $patrol;

        }
    }

    public function addPatrol(Request $request){

        $data = $request->all()['patrol_manager'];

        $patrol_manager = json_decode($data, true);

        $user = $patrol_manager['user'];
        $logs = $patrol_manager['temp_items'];



        // Insert new patrol to DB.
        $patrol = new \App\Patrol();
        $patrol->user = $user;
        $patrol->save();


        foreach($logs as $log){
            $location_id = $log['id'];
            $temp = $log['temp'];
            $patrol_id = $patrol->id;
            $checked = $log['checked'];

            if($checked == 0 || $checked == '0'){
                $temp = 0.0;
            }

            $log_db = new \App\Log();
            $log_db->location_id = $location_id;
            $log_db->temperature = $temp;
            $log_db->patrol_id = $patrol_id;
            $log_db->save();
        }

        return $patrol_manager;
    }

}

