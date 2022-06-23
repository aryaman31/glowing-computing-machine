import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';

export default function Login({ name, setName }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    console.log(name);
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
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  />
            </div>
            <div className='login-form-field'>
                  <label>Password</label>
                  <input
                  id="password"
                  type="text"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  />
            </div>

            <button className='btn login-form-field' type="submit">Submit</button>

        </form>
      </div>
    
  )
}
