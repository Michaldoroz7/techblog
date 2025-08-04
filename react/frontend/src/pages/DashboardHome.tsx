import React from 'react';
import QuoteCard from '../components/QuoteCard';
import ActivityCard from '../components/ActivityCard';

const DashboardHome = () => {
  return (
    <>
      <ActivityCard />
      <br/>
      <QuoteCard />
    </>
  );
};

export default DashboardHome;
