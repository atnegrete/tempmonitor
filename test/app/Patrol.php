<?php

namespace App;


use Illuminate\Database\Eloquent\Model;

class Patrol extends Model
{

    protected $table = 'patrols';

    public $timestamps = true;

    protected $fillable = [
        'user', 'created_at', 'updated_at'
    ];

    public function logs(){
        return $this->hasMany('App\Log')->get();
    }


}
