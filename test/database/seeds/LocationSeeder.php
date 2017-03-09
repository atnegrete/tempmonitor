<?php

use Illuminate\Database\Seeder;

class LocationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('locations')->insert([
            'location' => 'Admin 1st Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'Admin 2nd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'Admin 3rd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.W. 1st Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.W. 2nd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.W. 3rd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.E. 1st Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.E. 2nd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N.E. 3rd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'S. Wing 1st Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'S. Wing 2nd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'S. Wing 3rd Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N. Annex Basement',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'N. Annex 1st Floor',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'District 1 Basement',
            'description' => 'Description'
        ]);

        DB::table('locations')->insert([
            'location' => 'District 1 1st Floor',
            'description' => 'Description'
        ]);
    }
}
