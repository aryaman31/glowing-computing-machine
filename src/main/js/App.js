import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import BookAppointmentPage from './BookAppointmentPage';
import ViewAppointmentPage from './ViewAppointmentPage';
import YourAppointmentPage from './YourAppointmentPage';
import AppointmentNote from './AppointmentNote';
import Login from './Login';
import AdminOrPatient from './AdminOrPatient';
import AdminLogin from './AdminLogin';
import AdminHome from './AdminHome.js';
import PatientBookingRequests from './PatientBookingRequests.js';

const KEY_1 = 'app.upcomings'
const KEY_2 = 'app.allAppoints'

function App() {

  const [name, setName] = useState('');

  const [adminName, setAdminName] = useState('')

  const [upcomings, setUpcoming] = useState([]);

  const [patientId, setPatientId] = useState();
  //get all appointments from database
  const [allAppoints, setAllAppoints] = useState([]) 

  // patients choosing doctors
  const [doctor, setDoctor] = useState()

  const doctors = ['Dr Smith', 'Dr Garcia', 'Dr Strange', 'Dr Jones']

  // useEffect(() => {
  //   fetch()
  // })

  useEffect(() => {
    const storedUpcomings = JSON.parse(localStorage.getItem(KEY_1))
    if (storedUpcomings) {
      setUpcoming(storedUpcomings)
    }
  }, [])

  useEffect(() => {
    localStorage.setItem(KEY_1, JSON.stringify(upcomings));
    console.log("IM HERREERREER");
  }, [upcomings])

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
  }, [allAppoints])

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Routes>

            <Route path="/" element = {<AdminOrPatient/>} />

            <Route path="/admin_login" element = {<AdminLogin adminName={adminName} setAdminName={setAdminName}/>} />

            <Route path="/admin_home" element={<AdminHome adminName={adminName}/>} />

            <Route path="/patient_booking_requests" element={<PatientBookingRequests/>} />


            // removed setPatientId=...
            <Route path="/patient_login" element={<Login name={name} setName={setName}/>}/>  


            <Route path="/home" element={<Home name={name}/>}/>

            <Route path="/bookings" 
              element={
                <BookAppointmentPage 
                  setUpcoming={setUpcoming} 
                  name={name} 
                  allAppoints={allAppoints} 
                  setAllAppoints={setAllAppoints} 
                  doctors={doctors}
                  doctor={doctor}
                  setDoctor={setDoctor} />              
              }
            />


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
