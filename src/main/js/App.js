import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import BookPageOne from './BookPageOne';
import BookPageTwo from './BookPageTwo.js';
import BookPageThree from './BookPageThree.js';
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

  const [name, setName] = useState('');

  const [adminName, setAdminName] = useState('')

  const [upcomings, setUpcoming] = useState([]);

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

    //for display
    const temp = [...allAppoints]
    setDisplayAppoints(returnNoSameSlots(temp))
  }, [allAppoints])

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Routes>


            <Route path="/" element = {<AdminOrPatient/>} />

            <Route path="/admin_login" element = {<AdminLogin adminName={adminName} setAdminName={setAdminName}/>} />

            <Route path="/admin_home" element={<AdminHome adminName={adminName}/>} />

            <Route path="/patient_booking_requests" 
              element={<PatientBookingRequests 
                          allAppoints={allAppoints} 
                          displayAppoints={displayAppoints}/>
                      } />


            // removed setPatientId=...
            <Route path="/patient_login" element={<Login name={name} setName={setName}/>}/>  


            <Route path="/home" element={<Home name={name}/>}/>

            // first booking page
            <Route path="/book_appointment" element={<BookPageOne name={name}/>} />
            // second booking page
            <Route path="/appointment_details" element={<BookPageTwo name={name} setProblem={setProblem} setDescription={setDescription}/>} />
            // third booking page
            <Route path="/appointment_slot"
            element={
              <BookPageThree 
                setUpcoming={setUpcoming} 
                name={name} 
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
