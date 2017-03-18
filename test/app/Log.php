<?php

namespace App;


use Illuminate\Database\Eloquent\Model;

class Log extends Model
{

    public $timestamps = false;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'temperature', 'location_id', 'patrol_id'
    ];

    public function patrol(){
        return $this->belongsTo('App\Patrol')->first();
    }


}
