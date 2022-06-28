import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';

export default function Login({ nhsNumber, setNhsNumber }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    // fetch(`/api/patientId?name=${name}`)
    //   .then(response => response.json())
    //   .then(data => {
    //     setPatientId(data);
    //     console.log(data)
    //   }).catch(() => {
    //     setPatientId(0);
    //   });

      fetch(`/api/patientId?name=${nhsNumber}`)
      .then(response => response.json())
      .then(data => {
        setNhsNumber(nhsNumber);
        console.log(data)
      }).catch(() => {
        setNhsNumber(0);
      });


    // console.log(name);
    console.log(password);

    navigate('/home')
  }

  return (
      <div className='fit-to-page column center'>
        <h1>Login</h1>
        <form onSubmit={handleSubmit} className='login-form center'>
            <div className='login-form-field'>
                <label>Name</label>
                  <input
                  id="name"
                  type="text"
                  value={nhsNumber}
                  onChange={(e) => setNhsNumber(e.target.value)}
                  />
            </div>
            <div className='login-form-field'>
                  <label>Password</label>
                  <input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  />
            </div>

            <button className='btn login-form-field' type="submit">Submit</button>

            <Link to="/" className='btn'>Back</Link>


        </form>
      </div>
    
  )
}
