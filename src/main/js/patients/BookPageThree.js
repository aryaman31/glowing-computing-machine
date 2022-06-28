import React, { useCallback, useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid';
import { Calendar, Views, dateFnsLocalizer } from 'react-big-calendar'
import format from "date-fns/format";
import parse from "date-fns/parse";
import startOfWeek from "date-fns/startOfWeek";
import getDay from "date-fns/getDay"
import "./../css/react-big-calendar.css"
import Slot from './Slot';
import { doctorDummyOptions } from './../admin/PatientBookingRequests';
import Select from 'react-select';


export const locales = {
  "en-GB": require("date-fns/locale/en-GB")
};

export const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales
});



export default function BookPageThree({ setUpcoming, nhsNumber, 
  setAllAppoints, doctor, setDoctor, problem, description, displayAppoints }) {


    const [startTime, setStartTime] = useState(new Date())
    const [endTime, setEndTime] = useState(new Date())  
    const [hasSelected, setHasSelected] = useState(false)

    // // lets the user do up to 5 slots 
    // const 


    const slotPropGetter = useCallback((date) => {
      
      const d = new Date(date)
      const s = new Date(startTime)
      const e = new Date(endTime)

      if (hasSelected && d >= s && d < e) {
        return ({
          className: 'slotDefault',
          
            style: {
              backgroundColor: 'grey',
            },    
        }) 
      } 
    }, [startTime, endTime, hasSelected])
    

    function handleSelectSlot({start, end}) {
      setStartTime(start)
      setEndTime(end)
      setHasSelected(true)

      console.log(start)
      console.log(end)
    }

    function handleSelectEvent(e) {
      setStartTime(e.start)
      setEndTime(e.end)
      setHasSelected(true)
      
      console.log("RESERVED SLOT CLICKED, SEND TO WAITING LIST")
    }

    
    function handleConfirmation(_e) {

      if (doctor === '' || problem === '' || description === '' || !startTime || !endTime ) return

          
      const options = {  weekday: 'long', month: 'long', day: 'numeric' }
      const printDate = new Date(startTime).toLocaleString('en-GB', options)

      const options2 = { hour: 'numeric', minute: 'numeric' }
      const printStartTime = new Date(startTime).toLocaleString('en-GB', options2)
      const printEndTime = new Date(endTime).toLocaleString('en-GB', options2)

      const newId = uuidv4()

      //add to upcomings list
      setUpcoming(prev => {
        return [...prev, { id: newId, 
          doctor: doctor,
          time: printDate + ' ' + printStartTime + '-' + printEndTime, 
          problem: problem, 
          description: description}]
      })

      //add to all appointments
      setAllAppoints(prev => {
        return [...prev, { start: startTime, end: endTime, title: "lul",
        id: newId,
        nhsNumber: nhsNumber, 
        doctor: doctor,
        time: printDate + ' ' + printStartTime + '-' + printEndTime, 
        problem: problem, 
        description: description,
        confirm: false}]
      })

      //TODO: actually use the data and send to database
      // console.log(name)
      console.log(doctor)
      console.log(problem)
      console.log(description)
      console.log(printDate + ' ' + printStartTime + '-' + printEndTime)

      //fetch(`/api/requestBooking?patient_id=${patiendId}&gp_id`)
  
    }

    return (
        <>
         <h2>Pick an appointment slot</h2>

         <hr/>


         <div>
            <p>Please select a doctor you would like to meet</p>

            <Select
              options={doctorDummyOptions}
              onChange={(e) => {setDoctor(e.value)}} 
              placeholder="Choose a doctor from the dropdown below"
            />
            
        </div>


            

            {/* // TODO: Enable multiple slots  */}
        <p>Please select a date you would like to have your appointment.</p> 
        <p>Blue boxes indicates that the slot has already been reserved</p>
        <p>You can choose to be notified when a reserved slot becomes available by clicking it.</p>
        
        <div className="App">
          <Calendar
            localizer={localizer}
            defaultView={Views.WEEK}
            step = {15}
            timeslots = {1}
            views = {['week']}
            events = {displayAppoints}
            onSelectEvent={handleSelectEvent}
            onSelectSlot={handleSelectSlot}
            selectable
            onSelecting={() => false}
            style={{ height: 400, width: 550, margin: "25px" }}
            slotPropGetter={slotPropGetter}
            min={new Date(2022, 0, 0, 8, 0, 0)}
            max={new Date(2022, 0, 0, 20, 0, 0)}
          />
        </div>

        <p>The appointments from 8:00 a.m. to 9:00 a.m. and 6:00 p.m. 
          to 8:00 p.m. will be redirected to the local hub.</p>

        <Slot startTime={startTime} endTime={endTime} hasSelected={hasSelected} />

        <br/>


        <div className='row'>
          <Link to="/view" onClick={handleConfirmation} className='btn'>Create appointment</Link>

          <Link to="/home" className='btn'>Cancel</Link>
        </div>  



            <h3> Step 3 of 3</h3>
      
        </>
    )
}