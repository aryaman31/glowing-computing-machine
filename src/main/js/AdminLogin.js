import React, { useState } from 'react'
import { useNavigate } from 'react-router';

export default function AdminLogin({ adminName, setAdminName }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    console.log(name);
    console.log(password);

    navigate('/admin_home')
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
                  value={adminName}
                  onChange={(e) => setAdminName(e.target.value)}
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

            <button type="submit" className='btn login-form-field'>Submit</button>

        </form>
  {/* 
        <div className='options'>

          <Link to="/">
          <button>Back</button>
          </Link>

          </div> */}
    </div>

    
  )
}
