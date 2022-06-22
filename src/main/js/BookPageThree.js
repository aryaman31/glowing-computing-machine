import React, { useCallback, useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid';
import { Calendar, Views, dateFnsLocalizer } from 'react-big-calendar'
import format from "date-fns/format";
import parse from "date-fns/parse";
import startOfWeek from "date-fns/startOfWeek";
import getDay from "date-fns/getDay"
import "./react-big-calendar.css"
import Slot from './Slot';


const locales = {
  "en-GB": require("date-fns/locale/en-GB")
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales
});



export default function BookPageThree({ setUpcoming, name, allAppoints, setAllAppoints, doctor, setDoctor, problem, description }) {

  
    const [startTime, setStartTime] = useState(new Date())
    const [endTime, setEndTime] = useState(new Date())  
    const [hasSelected, setHasSelected] = useState(false)
    
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
  
    function handleSelectedDoctor(doctor) {
      setDoctor(doctor)
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
        return [...prev, { start: startTime, end: endTime, title: "lul", id: newId}]
      })
     
    }

    return (
        <>
        <h1>Pick an appointment slot</h1>

        <hr/>


        <div>
            <p>Please select a doctor you would like to meet</p>
            
            <select id="doctors" value={doctor}  
            defaultValue={"default"}
                    onChange={(e) => setDoctor(e.target.value)}>
                <option value={"default"} disabled>
                Choose a doctor from the dropdown below
                </option>
                <option value="Dr Smith">Dr Smith</option>
                <option value="Dr Garcia">Dr Garcia</option>
                <option value="Dr Jones">Dr Jones</option>
            </select>

        <p><b>Selected doctor: </b> {doctor}</p>
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
                events = {allAppoints}
                onSelectEvent={handleSelectEvent}
                onSelectSlot={handleSelectSlot}
                selectable
                onSelecting={() => false}
                style={{ height: 400, width: 550, margin: "25px" }}
                slotPropGetter={slotPropGetter}
            />
        </div>

    <Slot startTime={startTime} endTime={endTime} hasSelected={hasSelected} />



    {/* <Link to="/appointment_details">
                <button onClick={handleConfirmation}>Back</button>
            </Link> */}

            <Link to="/view">
                <button onClick={handleConfirmation}>Create my appointment</button>
            </Link>



            <h3> Step 3 of 3</h3>
      
        </>
    )
}