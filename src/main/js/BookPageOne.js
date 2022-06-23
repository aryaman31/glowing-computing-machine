// Let's book an appointment
import React, { useCallback, useRef, useState } from 'react'
import { Link } from 'react-router-dom'

export default function BookPageOne({ name }) {




    return (
        <>
            <h1> Let's book an appointment, {name}! </h1>

            <hr/>

            <h2> Step 1: Please consider whether your condition is appropriate to see a GP </h2>

            <div style={{
                alignItems: 'center',
                justifyContent: 'center',
            }}>

            <p> Firstly, are you experiencing any of the following symptoms? </p>

            <ul>
                <li>Chest pain</li>
                <li>Broken bones</li>
                <li>Bleeding that won't stop</li>
                <li>Difficulty completing a sentence</li>
            </ul>

            <h3> If so, your condition is too urgent to see a GP, please go to A and E or dial 999. </h3>

            </div>

            <div className='options row'>

                <Link to="/home" className='btn'> Back</Link>

                <br/>

                <Link to="/appointment_details" className='btn'>Next</Link>

                <br/>
    
            </div>

            <br/>  

            <h3> Step 1 of 3</h3>


        </>

    )


}