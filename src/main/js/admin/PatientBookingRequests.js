import React, { Fragment, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Calendar, Views } from 'react-big-calendar'
import "./../css/react-big-calendar.css"
import PatientAppointment from './../patients/PatientAppointment';
import { localizer } from './../patients/BookPageThree';
import Select from 'react-select';
import { returnNoSameSlots } from './../App';

export const doctorDummyOptions = [{value: 'Dr Smith' , label: 'Dr Smith'},
{value: 'Dr Garcia' , label: 'Dr Garcia'},
{value: 'Dr Jones' , label: 'Dr Jones'} ]

export default function PatientBookingRequests({ allAppoints, setAllAppoints }) {

  const [thisDaySlots, setThisDaySlots] = useState([])
  const [currDoctor, setCurrDoctor] = useState("")
  const [doctorSlots, setDoctorSlots] = useState([])
  const [currSlot, setCurrSlot] = useState()


  useEffect(() => {
    console.log({currDoctor})
    const temp = [...allAppoints]
    const res = temp.filter(elem => elem.doctor === currDoctor)
    //returnNoSameSlot so no overlap in display
    setDoctorSlots(returnNoSameSlots(res))
    setThisDaySlots([])
  }, [currDoctor])

  useEffect(() => {
    if (currDoctor) {
      console.log({currDoctor})
      const temp = [...allAppoints]
      const res = temp.filter(elem => elem.doctor === currDoctor)
      //returnNoSameSlot so no overlap in display
      setDoctorSlots(returnNoSameSlots(res))
    }
    if (currSlot) {
      const temp = [...allAppoints]
      console.log(currSlot.start)
      const thisSlotApppoints = temp.filter(elem => elem.start.getTime() === currSlot.start.getTime() && elem.doctor === currDoctor)
      setThisDaySlots(thisSlotApppoints)
    }
  }, [allAppoints])


  function handleSelectEvent(e) {
    setCurrSlot(e)
    const temp = [...allAppoints]
    console.log(e.start)
    const thisSlotApppoints = temp.filter(elem => elem.start.getTime() === e.start.getTime() && elem.doctor === currDoctor)
    setThisDaySlots(thisSlotApppoints)
  }

  function handleSendConfirmation(a) {

    if (!a.confirm) {
      const temp = [...allAppoints]

      for (let i = 0; i < temp.length; i++) {
       
        if (temp[i].confirm && (temp[i].start === a.start)) {
          temp[i].confirm = false
        }
        
        if (temp[i].id === a.id) {
          temp[i].confirm = true
        }
      }

      setAllAppoints(temp)


      console.log(a)

      console.log("i am the patient")

      console.log(a.name)

      const details = a.description.split('\n')

      const subject = "Confirmed Booking"
      const msg = `We have confirmed your booking at the gp. Check below for the details of your appointment.<br> Appointment Details:<br>Appointment time: ${a.time} <br>Doctor: ${a.doctor} <br>Problem faced: ${a.problem} <br>${details[0]} <br>${details[1]} <br>${details[2]} <br>${details[3]} <br>${details[4]} <br>${details[5]} <br>${details[6]} <br>`

      //send email
      fetch(`/api/email?to=dummy.patient26@gmail.com&subject=${subject}&msg=${msg}`);
    }

  }



  return (
    <>
        <h1>View patient's booking requests below</h1>
        <div className='card'>
        <p>Please select a doctor to view their appointments</p>


        <Select
          options={doctorDummyOptions}
          onChange={(e) => {setCurrDoctor(e.value)}} 
          placeholder="Choose a doctor from the dropdown below"
        />
        </div>

        <div style={{margin: "20px"}}>
        <p><b>Currently viewing the appointments of: </b> {currDoctor}</p>

        <hr/>


        <div className="App">
          <Calendar
            localizer={localizer}
            defaultView={Views.WEEK}
            step = {15}
            timeslots = {1}
            views = {['week']}
            events = {doctorSlots}
            onSelectEvent={handleSelectEvent}
            selectable
            onSelecting={() => false}
            style={{ height: 400, width: 550, margin: "25px" }}
            min={new Date(2022, 0, 0, 8, 0, 0)}
            max={new Date(2022, 0, 0, 20, 0, 0)}
          />
        </div>

        <hr/>

        <h3>Reserved appointment slots:</h3>

        <div className='currSlotAppointments'>
          {thisDaySlots.map(a => {
            return (
              <Fragment key={a.id}>
                <div className='thin-card'>
                {a.confirm && <h2 style={{color: 'green'}}>Confirmed Slot</h2>}
                <PatientAppointment appoint={a}/>
                <button className='btn' onClick={() => handleSendConfirmation(a)}>Send confirmation</button>
                </div>
                <hr/>
              </Fragment>
            )
          })}
          
        </div>
        </div>

        <br/>
        <div className='row'>
          <Link to="/admin_home" className='btn'>Back</Link>
        </div>
    </>
        
  )
}
