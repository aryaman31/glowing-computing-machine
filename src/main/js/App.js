import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './patients/Home';
import BookPageOne from './patients/BookPageOne';
import BookPageTwo from './patients/BookPageTwo.js';
import BookPageThree from './patients/BookPageThree.js';
import ViewAppointmentPage from './patients/ViewAppointmentPage';
import YourAppointmentPage from './patients/YourAppointmentPage';
import AppointmentNote from './widgets/AppointmentNote';
import Login from './patients/Login';
import AdminOrPatient from './AdminOrPatient';
import AdminLogin from './admin/AdminLogin';
import AdminHome from './admin/AdminHome.js';
import PatientBookingRequests from './admin/PatientBookingRequests.js';

const KEY_1 = 'app.upcomings'
const KEY_2 = 'app.allAppoints'

//only for passing in allAppoints to remove same slot appointments
//used for displaying the calendar
export function returnNoSameSlots(arr) {
  const temp = [...arr]
  const res = []
  var tempElem = temp[0]

  if (tempElem) {
    res.push(tempElem)
  }

  for (let i = 1; i < temp.length; i++) {
    console.log(tempElem.start)
    console.log(temp[i].start)
    if (tempElem.start.getTime() !== temp[i].start.getTime()) {
      res.push(temp[i])
    }
    tempElem = temp[i]
  }

  return res
}

function App() {


  const [adminName, setAdminName] = useState('')

  const [upcomings, setUpcoming] = useState([]);

  const [nhsNumber, setNhsNumber] = useState();

  // nhs number replaces the below:
  const [name, setName] = useState('');
  const [patientId, setPatientId] = useState();
  //get all appointments from database
  const [allAppoints, setAllAppoints] = useState([]) 

  //get array of appointments from allAppoints but without same slot
  //used only for calendar display
  const [displayAppoints, setDisplayAppoints] = useState([])

  // patients choosing doctors
  const [doctor, setDoctor] = useState()


  const [problem, setProblem] = useState()

  const [description, setDescription] = useState()


  // useEffect(() => {
  //   fetch()
  // })

  // useEffect(() => {
  //   const storedUpcomings = JSON.parse(localStorage.getItem(KEY_1))
  //   if (storedUpcomings) {
  //     setUpcoming(storedUpcomings)
  //   }
  // }, [])

  // useEffect(() => {
  //   localStorage.setItem(KEY_1, JSON.stringify(upcomings));
  //   console.log("IM HERREERREER");
  // }, [upcomings])

  useEffect(() => {
    const storedAllAppoints = JSON.parse(localStorage.getItem(KEY_2))
    if (storedAllAppoints) {
      storedAllAppoints.forEach(element => {
        const s = element.start;
        const e = element.end;
        element.start = new Date(s)
        element.end = new Date(e)
      });
      setAllAppoints(storedAllAppoints)
    }
  }, [])

  useEffect(() => {
    localStorage.setItem(KEY_2, JSON.stringify(allAppoints));
    console.log("IM HERREERREER");

    //for display
    const temp = [...allAppoints]
    setDisplayAppoints(returnNoSameSlots(temp))
  }, [allAppoints])

  useEffect(() => {
    //for current patient
    const upcomingTemp = [...allAppoints]
    const currPatientUpcoming = upcomingTemp.filter((elem) => elem.name === name)
    setUpcoming(currPatientUpcoming)
  }, [allAppoints, name])

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Routes>


            <Route path="/" element = {<AdminOrPatient/>} />

            <Route path="/admin_login" element = {<AdminLogin adminName={adminName} setAdminName={setAdminName}/>} />

            <Route path="/admin_home" element={<AdminHome adminName={adminName}/>} />

            <Route path="/patient_booking_requests" 
              element={<PatientBookingRequests allAppoints={allAppoints} setAllAppoints={setAllAppoints} />} />


            // removed setPatientId=...
            <Route path="/patient_login" element={<Login nhsNumber={nhsNumber} setNhsNumber={setNhsNumber}/>}/>  


            <Route path="/home" element={<Home nhsNumber={nhsNumber}/>}/>

            // first booking page
            <Route path="/book_appointment" element={<BookPageOne nhsNumber={nhsNumber}/>} />
            // second booking page
            <Route path="/appointment_details" element={<BookPageTwo nhsNumber={nhsNumber} setProblem={setProblem} setDescription={setDescription}/>} />
            // third booking page
            <Route path="/appointment_slot"
            element={
              <BookPageThree 
                setUpcoming={setUpcoming} 
                nhsNumber={nhsNumber} 
                setAllAppoints={setAllAppoints} 
                doctor={doctor}
                setDoctor={setDoctor}
                problem={problem}
                description={description}
                displayAppoints={displayAppoints}
                 />              
            } />

            {/* <Route path="/bookings" 
              element={
                <BookAppointmentPage 
                  setUpcoming={setUpcoming} 
                  name={name} 
                  allAppoints={allAppoints} 
                  setAllAppoints={setAllAppoints} 
                  doctor={doctor}
                  setDoctor={setDoctor}
                   />              
              }
            /> */}


            <Route path="/view" 
              element={
                <ViewAppointmentPage 
                  upcomings={upcomings} 
                  setUpcoming={setUpcoming}
                  allAppoints={allAppoints} 
                  setAllAppoints={setAllAppoints} />
              }
            />


            <Route path="/your_appointment/:aid" 
              element={
                <YourAppointmentPage upcomings={upcomings} />
              }
            />

            <Route path="/note" 
              element={
                <AppointmentNote/>
              }
            />


          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
