import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import BookAppointmentPage from './BookAppointmentPage';
import ViewAppointmentPage from './ViewAppointmentPage';
import YourAppointmentPage from './YourAppointmentPage';
import AppointmentNote from './AppointmentNote';
import Login from './Login';

function App() {

  const [name, setName] = useState('');

  const [upcomings, setUpcoming] = useState([]);

  //get all appointments from database
  const [allAppoints, setAllAppoints] = useState([]) 

  return (
    <Router>
      <div className='app'>
        <div className='content'>
          <Routes>

            <Route path="/" element={<Login name={name} setName={setName}/>}/>  


            <Route path="/home" element={<Home name={name}/>}/>


            <Route path="/bookings" 
              element={
                <BookAppointmentPage 
                  setUpcoming={setUpcoming} 
                  name={name} 
                  allAppoints={allAppoints} 
                  setAllAppoints={setAllAppoints} 
                />
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
