export type User = {
  name: string;
  profile_image: string;
};

export type Music = {
  title: string;
  artist: string;
  albumart: string;
  id: string;
};

export type Comment = {
  user: User;
  comment: {
    content: string;
    parent: any;
    last_modified: any;
    likes: any;
    userlike: any;
  };
};

export type FeedPost = {
  title: string;
  likes: any;
  article: string;
  tags: string;
  last_modified: any;
  user: User;
  playlist: Array<Music>;
  comments: Array<Comment>;
};

export const HomeFeedData: Array<FeedPost> = [
  {
    title: 'Track_Emocore',
    likes: '10.2k',
    article:
      'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
      'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
      'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ' +
      "It is a rock opera centering on a dying character with cancer known as 'The Patient'. " +
      'The album tells the story of his apparent death, experiences in the afterlife, and subsequent ' +
      "reflections on his life. It is the band's only studio album to feature drummer Bob Bryar before his departure in 2010.",
    tags: '#MCR #emocore #black_parade',
    last_modified: '1일',
    user: {
      name: 'mocha_oca',
      profile_image: 'https://bit.ly/3A2BqqJ',
    },
    playlist: [
      {
        title: 'Stressed Out',
        artist: '​twenty one pilots',
        albumart: 'https://i.ytimg.com/vi/Gc4sY98Jn9I/hqdefault.jpg',
        id: 'Gc4sY98Jn9I',
      },
      {
        title: 'Welcome To The Black Parade',
        artist: 'My Chemical Romance',
        albumart: 'https://i.ytimg.com/vi/G-I9csAflBs/hqdefault.jpg',
        id: 'G-I9csAflBs',
      },
      {
        title: 'LA Devotee',
        artist: 'Panic! At The Disco',
        albumart: 'https://i.ytimg.com/vi/RzMITAmJfeg/hqdefault.jpg',
        id: 'RzMITAmJfeg',
      },
      {
        title: '백색왜성',
        artist: '넬',
        albumart: 'https://i.ytimg.com/vi/qRaAvEbKmt0/hqdefault.jpg',
        id: 'qRaAvEbKmt0',
      },
      {
        title: 'Dead!',
        artist: 'My Chemical Romance',
        albumart: 'https://i.ytimg.com/vi/H48kOqqaWv0/hqdefault.jpg',
        id: 'H48kOqqaWv0',
      },
    ],
    comments: [
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'nice',
          parent: null,
          last_modified: '1분',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'I must use Lorem',
          parent: ' ',
          last_modified: '1시간',
          likes: 22,
          userlike: true,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'Good Old Days',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
    ],
  },
  {
    title: 'SimpleIsBest',
    likes: '22',
    article: 'no more confusion.',
    tags: '#void',
    last_modified: '1주',
    user: {
      name: 'slavv',
      profile_image: 'https://bit.ly/3Qvuqb4',
    },
    playlist: [
      {
        title: 'LA Devotee',
        artist: 'Panic! At The Disco',
        albumart: 'https://i.ytimg.com/vi/RzMITAmJfeg/maxresdefault.jpg',
        id: 'RzMITAmJfeg',
      },
      {
        title: 'Stressed Out',
        artist: '​twenty one pilots',
        albumart: 'https://i.ytimg.com/vi/Gc4sY98Jn9I/maxresdefault.jpg',
        id: 'Gc4sY98Jn9I',
      },
      {
        title: 'Welcome To The Black Parade',
        artist: 'My Chemical Romance',
        albumart: 'https://i.ytimg.com/vi/G-I9csAflBs/maxresdefault.jpg',
        id: 'G-I9csAflBs',
      },
      {
        title: 'TOMBOY',
        artist: '혁오',
        albumart: 'https://i.ytimg.com/vi/zC8RrOA0spo/hqdefault.jpg',
        id: 'zC8RrOA0spo',
      },
      {
        title: 'Dead!',
        artist: 'My Chemical Romance',
        albumart: 'https://i.ytimg.com/vi/H48kOqqaWv0/hqdefault.jpg',
        id: 'H48kOqqaWv0',
      },
    ],
    comments: [
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'nice',
          parent: null,
          last_modified: '1분',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'I must use Lorem',
          parent: ' ',
          last_modified: '1시간',
          likes: 22,
          userlike: true,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content: 'Good Old Days',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
      {
        user: {
          name: 'mocha_oca',
          profile_image: 'https://bit.ly/3A2BqqJ',
        },
        comment: {
          content:
            'The Black Parade is the third studio album by American rock band My Chemical Romance. ' +
            'Released in Europe on October 20, 2006, through Reprise Records, it was produced by the band with ' +
            'Rob Cavallo, known for having produced multiple albums for the Goo Goo Dolls and Green Day. ',
          parent: null,
          last_modified: '2시간',
          likes: 0,
          userlike: false,
        },
      },
    ],
  },
];