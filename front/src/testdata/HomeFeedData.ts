export type User = {
  name: string;
  profile_image: string;
};

export type Music = {
  title: string;
  artist: string;
  albumart: string;
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
  playlists: Array<Music>;
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
    playlists: [
      {
        title: 'Welcome To The Black Parade',
        artist: 'My Chemical Romance',
        albumart: 'https://bit.ly/3PXNy1o',
      },
      {
        title: 'LA Devotee',
        artist: 'Panic! At The Disco',
        albumart: 'https://bit.ly/3QdDcu6',
      },
      {
        title: '백색왜성',
        artist: '넬',
        albumart: 'https://bit.ly/3bwSzPF',
      },
      {
        title: 'Stressed Out',
        artist: '​twenty one pilots',
        albumart: 'https://bit.ly/3PcIrtn',
      },
      {
        title: 'Dead!',
        artist: 'My Chemical Romance',
        albumart: 'https://bit.ly/3PXNy1o',
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
